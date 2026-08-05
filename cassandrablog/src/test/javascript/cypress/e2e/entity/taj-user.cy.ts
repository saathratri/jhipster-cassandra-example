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

describe('TajUser e2e test', () => {
  const tajUserPageUrl = '/cassandrablog/taj-user';
  const tajUserPageUrlPattern = new RegExp('/cassandrablog/taj-user(\\?.*)?$');
  let username: string;
  let password: string;
  const tajUserSample = { id: '00000000-0000-4000-8000-000000000001', login: 'depend unless bashfully' };

  let tajUser;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/taj-users\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/taj-users').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/taj-users/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (tajUser) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/taj-users/${tajUser.id}`,
      }).then(() => {
        tajUser = undefined;
      });
    }
  });

  it('TajUsers menu should load TajUsers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/taj-user');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TajUser').should('exist');
    cy.url().should('match', tajUserPageUrlPattern);
  });

  describe('TajUser page', () => {
    it('should have translated page title', () => {
      cy.visit(tajUserPageUrl);
      cy.getEntityHeading('TajUser').should('not.contain', 'cassandrablogApp.cassandrablogTajUser.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(tajUserPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create TajUser page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/taj-user/new$'));
        cy.getEntityCreateUpdateHeading('TajUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/taj-users',
          body: tajUserSample,
        }).then(({ body }) => {
          tajUser = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/taj-users\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [tajUser],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(tajUserPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details TajUser page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('tajUser');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);
      });

      it('edit button click should load edit TajUser page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TajUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);
      });

      it('edit button click should load edit TajUser page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TajUser');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);
      });

      it('last delete button click should delete instance of TajUser', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('tajUser').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);

        tajUser = undefined;
      });
    });
  });

  describe('new TajUser page', () => {
    beforeEach(() => {
      cy.visit(tajUserPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TajUser');
    });

    it('should create an instance of TajUser', () => {
      cy.get(`[data-cy="id"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="id"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="login"]`).type('hassle happily');
      cy.get(`[data-cy="login"]`).should('have.value', 'hassle happily');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        tajUser = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', tajUserPageUrlPattern);
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(tajUserPageUrl.substring(1));
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
