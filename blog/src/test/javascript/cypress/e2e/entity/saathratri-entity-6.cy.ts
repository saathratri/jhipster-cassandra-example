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

describe('SaathratriEntity6 e2e test', () => {
  const saathratriEntity6PageUrl = '/blog/saathratri-entity-6';
  const saathratriEntity6PageUrlPattern = new RegExp('/blog/saathratri-entity-6(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const saathratriEntity6Sample = {};

  let saathratriEntity6;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/saathratri-entity-6-s+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/saathratri-entity-6-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/saathratri-entity-6-s/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (saathratriEntity6) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/saathratri-entity-6-s/${saathratriEntity6.organizationId}`,
      }).then(() => {
        saathratriEntity6 = undefined;
      });
    }
  });

  it('SaathratriEntity6s menu should load SaathratriEntity6s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/saathratri-entity-6');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SaathratriEntity6').should('exist');
    cy.url().should('match', saathratriEntity6PageUrlPattern);
  });

  describe('SaathratriEntity6 page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(saathratriEntity6PageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SaathratriEntity6 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/saathratri-entity-6/new$'));
        cy.getEntityCreateUpdateHeading('SaathratriEntity6');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity6PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/saathratri-entity-6-s',
          body: saathratriEntity6Sample,
        }).then(({ body }) => {
          saathratriEntity6 = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/saathratri-entity-6-s+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [saathratriEntity6],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(saathratriEntity6PageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SaathratriEntity6 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('saathratriEntity6');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity6PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity6 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity6');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity6PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity6 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity6');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity6PageUrlPattern);
      });

      it('last delete button click should delete instance of SaathratriEntity6', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('saathratriEntity6').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity6PageUrlPattern);

        saathratriEntity6 = undefined;
      });
    });
  });

  describe('new SaathratriEntity6 page', () => {
    beforeEach(() => {
      cy.visit(`${saathratriEntity6PageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SaathratriEntity6');
    });

    it('should create an instance of SaathratriEntity6', () => {
      cy.get(`[data-cy="arrivalDate"]`).type('30907');
      cy.get(`[data-cy="arrivalDate"]`).should('have.value', '30907');

      cy.get(`[data-cy="accountNumber"]`).type('upwardly yum or');
      cy.get(`[data-cy="accountNumber"]`).should('have.value', 'upwardly yum or');

      cy.get(`[data-cy="createdTimeId"]`).type('4cc566db-b71f-4900-bf51-07060d6ad2b8');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('4cc566db-b71f-4900-bf51-07060d6ad2b8'));

      cy.get(`[data-cy="departureDate"]`).type('6362');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '6362');

      cy.get(`[data-cy="customerId"]`).type('607eb718-ccf1-43a8-bb06-94368c4fd78f');
      cy.get(`[data-cy="customerId"]`).invoke('val').should('match', new RegExp('607eb718-ccf1-43a8-bb06-94368c4fd78f'));

      cy.get(`[data-cy="customerFirstName"]`).type('metallic');
      cy.get(`[data-cy="customerFirstName"]`).should('have.value', 'metallic');

      cy.get(`[data-cy="customerLastName"]`).type('swanling refer times');
      cy.get(`[data-cy="customerLastName"]`).should('have.value', 'swanling refer times');

      cy.get(`[data-cy="customerUpdatedEmail"]`).type('ew outlying');
      cy.get(`[data-cy="customerUpdatedEmail"]`).should('have.value', 'ew outlying');

      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).type('while deduction digestive');
      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).should('have.value', 'while deduction digestive');

      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).type('huzzah');
      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).should('have.value', 'huzzah');

      cy.get(`[data-cy="tinyUrlShortCode"]`).type('grouchy doodle aha');
      cy.get(`[data-cy="tinyUrlShortCode"]`).should('have.value', 'grouchy doodle aha');

      cy.get(`[data-cy="addOnDetailsText"]`).type('distant fixed');
      cy.get(`[data-cy="addOnDetailsText"]`).should('have.value', 'distant fixed');

      cy.get(`[data-cy="addOnDetailsDecimal"]`).type('5479.03');
      cy.get(`[data-cy="addOnDetailsDecimal"]`).should('have.value', '5479.03');

      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('not.be.checked');
      cy.get(`[data-cy="addOnDetailsBoolean"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('be.checked');

      cy.get(`[data-cy="addOnDetailsBigInt"]`).type('19978');
      cy.get(`[data-cy="addOnDetailsBigInt"]`).should('have.value', '19978');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        saathratriEntity6 = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', saathratriEntity6PageUrlPattern);
    });
  });
});
