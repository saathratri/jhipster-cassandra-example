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

describe('AddOnsSelectedByOrganization e2e test', () => {
  const addOnsSelectedByOrganizationPageUrl = '/blog/add-ons-selected-by-organization';
  const addOnsSelectedByOrganizationPageUrlPattern = new RegExp('/blog/add-ons-selected-by-organization(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const addOnsSelectedByOrganizationSample = {};

  let addOnsSelectedByOrganization;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/add-ons-selected-by-organizations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/add-ons-selected-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/add-ons-selected-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (addOnsSelectedByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/add-ons-selected-by-organizations/${addOnsSelectedByOrganization.organizationId}`,
      }).then(() => {
        addOnsSelectedByOrganization = undefined;
      });
    }
  });

  it('AddOnsSelectedByOrganizations menu should load AddOnsSelectedByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/add-ons-selected-by-organization');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AddOnsSelectedByOrganization').should('exist');
    cy.url().should('match', addOnsSelectedByOrganizationPageUrlPattern);
  });

  describe('AddOnsSelectedByOrganization page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(addOnsSelectedByOrganizationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AddOnsSelectedByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/add-ons-selected-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('AddOnsSelectedByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsSelectedByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/add-ons-selected-by-organizations',
          body: addOnsSelectedByOrganizationSample,
        }).then(({ body }) => {
          addOnsSelectedByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/add-ons-selected-by-organizations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [addOnsSelectedByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(addOnsSelectedByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AddOnsSelectedByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('addOnsSelectedByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsSelectedByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit AddOnsSelectedByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AddOnsSelectedByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsSelectedByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit AddOnsSelectedByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AddOnsSelectedByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsSelectedByOrganizationPageUrlPattern);
      });

      it('last delete button click should delete instance of AddOnsSelectedByOrganization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('addOnsSelectedByOrganization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsSelectedByOrganizationPageUrlPattern);

        addOnsSelectedByOrganization = undefined;
      });
    });
  });

  describe('new AddOnsSelectedByOrganization page', () => {
    beforeEach(() => {
      cy.visit(`${addOnsSelectedByOrganizationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AddOnsSelectedByOrganization');
    });

    it('should create an instance of AddOnsSelectedByOrganization', () => {
      cy.get(`[data-cy="arrivalDate"]`).type('22385');
      cy.get(`[data-cy="arrivalDate"]`).should('have.value', '22385');

      cy.get(`[data-cy="accountNumber"]`).type('round for rudely');
      cy.get(`[data-cy="accountNumber"]`).should('have.value', 'round for rudely');

      cy.get(`[data-cy="createdTimeId"]`).type('3edf7272-959f-4605-809b-458505d05421');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('3edf7272-959f-4605-809b-458505d05421'));

      cy.get(`[data-cy="departureDate"]`).type('3707');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '3707');

      cy.get(`[data-cy="customerId"]`).type('5c495799-9844-4a2d-a2b9-4794704a62e1');
      cy.get(`[data-cy="customerId"]`).invoke('val').should('match', new RegExp('5c495799-9844-4a2d-a2b9-4794704a62e1'));

      cy.get(`[data-cy="customerFirstName"]`).type('quizzically');
      cy.get(`[data-cy="customerFirstName"]`).should('have.value', 'quizzically');

      cy.get(`[data-cy="customerLastName"]`).type('fortunately hm');
      cy.get(`[data-cy="customerLastName"]`).should('have.value', 'fortunately hm');

      cy.get(`[data-cy="customerUpdatedEmail"]`).type('indeed dandelion');
      cy.get(`[data-cy="customerUpdatedEmail"]`).should('have.value', 'indeed dandelion');

      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).type('larva fatal technician');
      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).should('have.value', 'larva fatal technician');

      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).type('kiss overcook');
      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).should('have.value', 'kiss overcook');

      cy.get(`[data-cy="tinyUrlShortCode"]`).type('fooey wonderfully except');
      cy.get(`[data-cy="tinyUrlShortCode"]`).should('have.value', 'fooey wonderfully except');

      cy.get(`[data-cy="addOnDetailsText"]`).type('exalted');
      cy.get(`[data-cy="addOnDetailsText"]`).should('have.value', 'exalted');

      cy.get(`[data-cy="addOnDetailsDecimal"]`).type('4959.54');
      cy.get(`[data-cy="addOnDetailsDecimal"]`).should('have.value', '4959.54');

      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('not.be.checked');
      cy.get(`[data-cy="addOnDetailsBoolean"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('be.checked');

      cy.get(`[data-cy="addOnDetailsBigInt"]`).type('5192');
      cy.get(`[data-cy="addOnDetailsBigInt"]`).should('have.value', '5192');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        addOnsSelectedByOrganization = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', addOnsSelectedByOrganizationPageUrlPattern);
    });
  });
});
