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

describe('SetEntityByOrganization e2e test', () => {
  const setEntityByOrganizationPageUrl = '/cassandrablog/set-entity-by-organization';
  const setEntityByOrganizationPageUrlPattern = new RegExp('/cassandrablog/set-entity-by-organization(\\?.*)?$');
  let username: string;
  let password: string;
  const setEntityByOrganizationSample = { organizationId: '00000000-0000-4000-8000-000000000001' };

  let setEntityByOrganization;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/set-entity-by-organizations\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/set-entity-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/set-entity-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (setEntityByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/set-entity-by-organizations/${setEntityByOrganization.organizationId}`,
      }).then(() => {
        setEntityByOrganization = undefined;
      });
    }
  });

  it('SetEntityByOrganizations menu should load SetEntityByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/set-entity-by-organization');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SetEntityByOrganization').should('exist');
    cy.url().should('match', setEntityByOrganizationPageUrlPattern);
  });

  describe('SetEntityByOrganization page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(setEntityByOrganizationPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create SetEntityByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/set-entity-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('SetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/set-entity-by-organizations',
          body: setEntityByOrganizationSample,
        }).then(({ body }) => {
          setEntityByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/set-entity-by-organizations\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [setEntityByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(setEntityByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details SetEntityByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('setEntityByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit SetEntityByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit SetEntityByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);
      });

      it('last delete button click should delete instance of SetEntityByOrganization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('setEntityByOrganization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);

        setEntityByOrganization = undefined;
      });
    });
  });

  describe('new SetEntityByOrganization page', () => {
    beforeEach(() => {
      cy.visit(setEntityByOrganizationPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SetEntityByOrganization');
    });

    it('should create an instance of SetEntityByOrganization', () => {
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        setEntityByOrganization = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', setEntityByOrganizationPageUrlPattern);
    });

    it('should accept input on the tags SET widget add row', () => {
      cy.get(`[data-cy="tags-add-value"]`).type('sample-tags-1');
      cy.get(`[data-cy="tags-add-value"]`).should('have.value', 'sample-tags-1');
      cy.get(`[data-cy="tags-add-button"]`).should('not.be.disabled');
    });
  });
});
