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

describe('AddOnsAvailableByOrganization e2e test', () => {
  const addOnsAvailableByOrganizationPageUrl = '/blog/add-ons-available-by-organization';
  const addOnsAvailableByOrganizationPageUrlPattern = new RegExp('/blog/add-ons-available-by-organization(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const addOnsAvailableByOrganizationSample = {};

  let addOnsAvailableByOrganization;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/add-ons-available-by-organizations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/add-ons-available-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/add-ons-available-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (addOnsAvailableByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/add-ons-available-by-organizations/${addOnsAvailableByOrganization.organizationId}`,
      }).then(() => {
        addOnsAvailableByOrganization = undefined;
      });
    }
  });

  it('AddOnsAvailableByOrganizations menu should load AddOnsAvailableByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/add-ons-available-by-organization');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AddOnsAvailableByOrganization').should('exist');
    cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
  });

  describe('AddOnsAvailableByOrganization page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(addOnsAvailableByOrganizationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AddOnsAvailableByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/add-ons-available-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('AddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/add-ons-available-by-organizations',
          body: addOnsAvailableByOrganizationSample,
        }).then(({ body }) => {
          addOnsAvailableByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/add-ons-available-by-organizations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [addOnsAvailableByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(addOnsAvailableByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AddOnsAvailableByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('addOnsAvailableByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit AddOnsAvailableByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit AddOnsAvailableByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
      });

      it('last delete button click should delete instance of AddOnsAvailableByOrganization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('addOnsAvailableByOrganization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);

        addOnsAvailableByOrganization = undefined;
      });
    });
  });

  describe('new AddOnsAvailableByOrganization page', () => {
    beforeEach(() => {
      cy.visit(`${addOnsAvailableByOrganizationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AddOnsAvailableByOrganization');
    });

    it('should create an instance of AddOnsAvailableByOrganization', () => {
      cy.get(`[data-cy="entityType"]`).type('sparse swing');
      cy.get(`[data-cy="entityType"]`).should('have.value', 'sparse swing');

      cy.get(`[data-cy="entityId"]`).type('99565bf7-0ffc-49c6-a5b1-3e949cd12f1f');
      cy.get(`[data-cy="entityId"]`).invoke('val').should('match', new RegExp('99565bf7-0ffc-49c6-a5b1-3e949cd12f1f'));

      cy.get(`[data-cy="addOnId"]`).type('dd6d4bdf-b040-41e8-8b8e-72b7342394b9');
      cy.get(`[data-cy="addOnId"]`).invoke('val').should('match', new RegExp('dd6d4bdf-b040-41e8-8b8e-72b7342394b9'));

      cy.get(`[data-cy="addOnType"]`).type('gadzooks');
      cy.get(`[data-cy="addOnType"]`).should('have.value', 'gadzooks');

      cy.get(`[data-cy="addOnDetailsText"]`).type('pulverize than');
      cy.get(`[data-cy="addOnDetailsText"]`).should('have.value', 'pulverize than');

      cy.get(`[data-cy="addOnDetailsDecimal"]`).type('11615.68');
      cy.get(`[data-cy="addOnDetailsDecimal"]`).should('have.value', '11615.68');

      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('not.be.checked');
      cy.get(`[data-cy="addOnDetailsBoolean"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('be.checked');

      cy.get(`[data-cy="addOnDetailsBigInt"]`).type('6867');
      cy.get(`[data-cy="addOnDetailsBigInt"]`).should('have.value', '6867');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        addOnsAvailableByOrganization = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
    });
  });
});
