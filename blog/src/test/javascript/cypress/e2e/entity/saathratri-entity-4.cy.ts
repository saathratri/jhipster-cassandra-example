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

describe('SaathratriEntity4 e2e test', () => {
  const saathratriEntity4PageUrl = '/blog/saathratri-entity-4';
  const saathratriEntity4PageUrlPattern = new RegExp('/blog/saathratri-entity-4(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const saathratriEntity4Sample = {};

  let saathratriEntity4;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/saathratri-entity-4-s+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/saathratri-entity-4-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/saathratri-entity-4-s/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (saathratriEntity4) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/saathratri-entity-4-s/${saathratriEntity4.organizationId}`,
      }).then(() => {
        saathratriEntity4 = undefined;
      });
    }
  });

  it('SaathratriEntity4s menu should load SaathratriEntity4s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/saathratri-entity-4');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SaathratriEntity4').should('exist');
    cy.url().should('match', saathratriEntity4PageUrlPattern);
  });

  describe('SaathratriEntity4 page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(saathratriEntity4PageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SaathratriEntity4 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/saathratri-entity-4/new$'));
        cy.getEntityCreateUpdateHeading('SaathratriEntity4');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity4PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/saathratri-entity-4-s',
          body: saathratriEntity4Sample,
        }).then(({ body }) => {
          saathratriEntity4 = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/saathratri-entity-4-s+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [saathratriEntity4],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(saathratriEntity4PageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SaathratriEntity4 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('saathratriEntity4');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity4PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity4 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity4');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity4PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity4 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity4');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity4PageUrlPattern);
      });

      it('last delete button click should delete instance of SaathratriEntity4', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('saathratriEntity4').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity4PageUrlPattern);

        saathratriEntity4 = undefined;
      });
    });
  });

  describe('new SaathratriEntity4 page', () => {
    beforeEach(() => {
      cy.visit(`${saathratriEntity4PageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SaathratriEntity4');
    });

    it('should create an instance of SaathratriEntity4', () => {
      cy.get(`[data-cy="attributeKey"]`).type('kindheartedly intermesh alb');
      cy.get(`[data-cy="attributeKey"]`).should('have.value', 'kindheartedly intermesh alb');

      cy.get(`[data-cy="attributeValue"]`).type('yippee');
      cy.get(`[data-cy="attributeValue"]`).should('have.value', 'yippee');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        saathratriEntity4 = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', saathratriEntity4PageUrlPattern);
    });
  });
});
