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

describe('SaathratriEntity5 e2e test', () => {
  const saathratriEntity5PageUrl = '/blog/saathratri-entity-5';
  const saathratriEntity5PageUrlPattern = new RegExp('/blog/saathratri-entity-5(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const saathratriEntity5Sample = {};

  let saathratriEntity5;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/saathratri-entity-5-s+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/saathratri-entity-5-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/saathratri-entity-5-s/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (saathratriEntity5) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/saathratri-entity-5-s/${saathratriEntity5.organizationId}`,
      }).then(() => {
        saathratriEntity5 = undefined;
      });
    }
  });

  it('SaathratriEntity5s menu should load SaathratriEntity5s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/saathratri-entity-5');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SaathratriEntity5').should('exist');
    cy.url().should('match', saathratriEntity5PageUrlPattern);
  });

  describe('SaathratriEntity5 page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(saathratriEntity5PageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SaathratriEntity5 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/saathratri-entity-5/new$'));
        cy.getEntityCreateUpdateHeading('SaathratriEntity5');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity5PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/saathratri-entity-5-s',
          body: saathratriEntity5Sample,
        }).then(({ body }) => {
          saathratriEntity5 = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/saathratri-entity-5-s+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [saathratriEntity5],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(saathratriEntity5PageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SaathratriEntity5 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('saathratriEntity5');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity5PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity5 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity5');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity5PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity5 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity5');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity5PageUrlPattern);
      });

      it('last delete button click should delete instance of SaathratriEntity5', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('saathratriEntity5').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity5PageUrlPattern);

        saathratriEntity5 = undefined;
      });
    });
  });

  describe('new SaathratriEntity5 page', () => {
    beforeEach(() => {
      cy.visit(`${saathratriEntity5PageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SaathratriEntity5');
    });

    it('should create an instance of SaathratriEntity5', () => {
      cy.get(`[data-cy="entityType"]`).type('ew tenement');
      cy.get(`[data-cy="entityType"]`).should('have.value', 'ew tenement');

      cy.get(`[data-cy="entityId"]`).type('7c8e0c5a-7ecd-412a-9e9c-112d2744cc9f');
      cy.get(`[data-cy="entityId"]`).invoke('val').should('match', new RegExp('7c8e0c5a-7ecd-412a-9e9c-112d2744cc9f'));

      cy.get(`[data-cy="addOnId"]`).type('479b9004-8bb6-437c-b984-ecb75aa6364c');
      cy.get(`[data-cy="addOnId"]`).invoke('val').should('match', new RegExp('479b9004-8bb6-437c-b984-ecb75aa6364c'));

      cy.get(`[data-cy="addOnType"]`).type('unless fatally whether');
      cy.get(`[data-cy="addOnType"]`).should('have.value', 'unless fatally whether');

      cy.get(`[data-cy="addOnDetailsText"]`).type('unwelcome');
      cy.get(`[data-cy="addOnDetailsText"]`).should('have.value', 'unwelcome');

      cy.get(`[data-cy="addOnDetailsDecimal"]`).type('25517.11');
      cy.get(`[data-cy="addOnDetailsDecimal"]`).should('have.value', '25517.11');

      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('not.be.checked');
      cy.get(`[data-cy="addOnDetailsBoolean"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('be.checked');

      cy.get(`[data-cy="addOnDetailsBigInt"]`).type('17887');
      cy.get(`[data-cy="addOnDetailsBigInt"]`).should('have.value', '17887');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        saathratriEntity5 = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', saathratriEntity5PageUrlPattern);
    });
  });
});
