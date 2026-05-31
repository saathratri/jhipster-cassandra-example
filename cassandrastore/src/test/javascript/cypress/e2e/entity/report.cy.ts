import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Report e2e test', () => {
  const reportPageUrl = '/cassandrastore/report';
  const reportPageUrlPattern = new RegExp('/cassandrastore/report(\\?.*)?$');
  let username: string;
  let password: string;
  const reportSample = {
    id: '00000000-0000-4000-8000-000000000001',
    fileName: 'electronics yahoo well-off',
    fileExtension: 'some ferociously',
    createDate: 4558,
  };

  let report;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrastore\/api\/reports\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrastore/api/reports').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrastore/api/reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (report) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrastore/api/reports/${report.id}`,
      }).then(() => {
        report = undefined;
      });
    }
  });

  it('Reports menu should load Reports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrastore/report');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Report').should('exist');
    cy.url().should('match', reportPageUrlPattern);
  });

  describe('Report page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(reportPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create Report page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrastore/report/new$'));
        cy.getEntityCreateUpdateHeading('Report');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', reportPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrastore/api/reports',
          body: reportSample,
        }).then(({ body }) => {
          report = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrastore\/api\/reports\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [report],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(reportPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details Report page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('report');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', reportPageUrlPattern);
      });

      it('edit button click should load edit Report page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Report');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', reportPageUrlPattern);
      });

      it('edit button click should load edit Report page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Report');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', reportPageUrlPattern);
      });

      it('last delete button click should delete instance of Report', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('report').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', reportPageUrlPattern);

        report = undefined;
      });
    });
  });

  describe('new Report page', () => {
    beforeEach(() => {
      cy.visit(reportPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Report');
    });

    it('should create an instance of Report', () => {
      cy.get(`[data-cy="id"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="id"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="fileName"]`).type('um tinderbox');
      cy.get(`[data-cy="fileName"]`).should('have.value', 'um tinderbox');

      cy.get(`[data-cy="fileExtension"]`).type('downchange');
      cy.get(`[data-cy="fileExtension"]`).should('have.value', 'downchange');
      cy.setFieldImageAsBytesOfEntity('file', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="approved"]`).should('not.be.checked');
      cy.get(`[data-cy="approved"]`).click();
      cy.get(`[data-cy="approved"]`).should('be.checked');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(`[data-cy="createDate-generate"]`).click({ force: true });

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        report = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', reportPageUrlPattern);
    });

    it('should accept input on the createDate date-time widget sub-inputs', () => {
      cy.get(`[data-cy="createDate-hours"]`).invoke('val', '10').trigger('input', { force: true });
      cy.get(`[data-cy="createDate-hours"]`).should('have.value', '10');

      cy.get(`[data-cy="createDate-minutes"]`).invoke('val', '30').trigger('input', { force: true });
      cy.get(`[data-cy="createDate-minutes"]`).should('have.value', '30');

      cy.get(`[data-cy="createDate-ampm"]`).click({ force: true });
      cy.get('mat-option').contains('AM').click({ force: true });
      cy.get(`[data-cy="createDate-ampm"]`).should('contain', 'AM');
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(reportPageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="id-generate"]`).click();
    cy.get(`[data-cy="id"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="id-reset"]`).click();
    cy.get(`[data-cy="id"]`).should('have.value', '');
  });
});
