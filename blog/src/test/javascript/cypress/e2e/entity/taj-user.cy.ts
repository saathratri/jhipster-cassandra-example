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
  const tajUserPageUrl = '/blog/taj-user';
  const tajUserPageUrlPattern = new RegExp('/blog/taj-user(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const tajUserSample = { login: 'but now' };

  let tajUser;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/taj-users+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/taj-users').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/taj-users/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (tajUser) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/taj-users/${tajUser.id}`,
      }).then(() => {
        tajUser = undefined;
      });
    }
  });

  it('TajUsers menu should load TajUsers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/taj-user');
    cy.wait('@entitiesRequest').then(({ response }) => {
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
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(tajUserPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TajUser page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/taj-user/new$'));
        cy.getEntityCreateUpdateHeading('TajUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/taj-users',
          body: tajUserSample,
        }).then(({ body }) => {
          tajUser = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/taj-users+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [tajUser],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(tajUserPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TajUser page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('tajUser');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);
      });

      it('edit button click should load edit TajUser page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TajUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);
      });

      it('edit button click should load edit TajUser page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TajUser');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
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
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tajUserPageUrlPattern);

        tajUser = undefined;
      });
    });
  });

  describe('new TajUser page', () => {
    beforeEach(() => {
      cy.visit(`${tajUserPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TajUser');
    });

    it('should create an instance of TajUser', () => {
      cy.get(`[data-cy="login"]`).type('masquerade blissfully uh-huh');
      cy.get(`[data-cy="login"]`).should('have.value', 'masquerade blissfully uh-huh');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        tajUser = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', tajUserPageUrlPattern);
    });
  });
});
