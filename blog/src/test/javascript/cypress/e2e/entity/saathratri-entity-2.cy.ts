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

describe('SaathratriEntity2 e2e test', () => {
  const saathratriEntity2PageUrl = '/blog/saathratri-entity-2';
  const saathratriEntity2PageUrlPattern = new RegExp('/blog/saathratri-entity-2(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const saathratriEntity2Sample = {};

  let saathratriEntity2;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/saathratri-entity-2-s+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/saathratri-entity-2-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/saathratri-entity-2-s/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (saathratriEntity2) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/saathratri-entity-2-s/${saathratriEntity2.entityTypeId}`,
      }).then(() => {
        saathratriEntity2 = undefined;
      });
    }
  });

  it('SaathratriEntity2s menu should load SaathratriEntity2s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/saathratri-entity-2');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SaathratriEntity2').should('exist');
    cy.url().should('match', saathratriEntity2PageUrlPattern);
  });

  describe('SaathratriEntity2 page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(saathratriEntity2PageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SaathratriEntity2 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/saathratri-entity-2/new$'));
        cy.getEntityCreateUpdateHeading('SaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/saathratri-entity-2-s',
          body: saathratriEntity2Sample,
        }).then(({ body }) => {
          saathratriEntity2 = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/saathratri-entity-2-s+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [saathratriEntity2],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(saathratriEntity2PageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SaathratriEntity2 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('saathratriEntity2');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity2 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity2 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);
      });

      it('last delete button click should delete instance of SaathratriEntity2', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('saathratriEntity2').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);

        saathratriEntity2 = undefined;
      });
    });
  });

  describe('new SaathratriEntity2 page', () => {
    beforeEach(() => {
      cy.visit(`${saathratriEntity2PageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SaathratriEntity2');
    });

    it('should create an instance of SaathratriEntity2', () => {
      cy.get(`[data-cy="yearOfDateAdded"]`).type('29079');
      cy.get(`[data-cy="yearOfDateAdded"]`).should('have.value', '29079');

      cy.get(`[data-cy="arrivalDate"]`).type('17603');
      cy.get(`[data-cy="arrivalDate"]`).should('have.value', '17603');

      cy.get(`[data-cy="blogId"]`).type('d73a791b-eaf7-431c-abad-846d1ab1eed3');
      cy.get(`[data-cy="blogId"]`).invoke('val').should('match', new RegExp('d73a791b-eaf7-431c-abad-846d1ab1eed3'));

      cy.get(`[data-cy="entityName"]`).type('malfunction provided');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'malfunction provided');

      cy.get(`[data-cy="entityDescription"]`).type('unaccountably repentant tremendously');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'unaccountably repentant tremendously');

      cy.get(`[data-cy="entityCost"]`).type('23710.86');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '23710.86');

      cy.get(`[data-cy="departureDate"]`).type('31321');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '31321');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        saathratriEntity2 = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', saathratriEntity2PageUrlPattern);
    });
  });
});
