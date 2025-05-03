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
      cy.get(`[data-cy="entityType"]`).type('boldly instead');
      cy.get(`[data-cy="entityType"]`).should('have.value', 'boldly instead');

      cy.get(`[data-cy="entityId"]`).type('c880415f-39b5-4133-b1d6-425e4fab3e8e');
      cy.get(`[data-cy="entityId"]`).invoke('val').should('match', new RegExp('c880415f-39b5-4133-b1d6-425e4fab3e8e'));

      cy.get(`[data-cy="addOnId"]`).type('26417f9f-30a2-4228-acc2-88e7935c1c77');
      cy.get(`[data-cy="addOnId"]`).invoke('val').should('match', new RegExp('26417f9f-30a2-4228-acc2-88e7935c1c77'));

      cy.get(`[data-cy="addOnType"]`).type('breed calmly');
      cy.get(`[data-cy="addOnType"]`).should('have.value', 'breed calmly');

      cy.get(`[data-cy="addOnDetailsText"]`).type('before indeed how');
      cy.get(`[data-cy="addOnDetailsText"]`).should('have.value', 'before indeed how');

      cy.get(`[data-cy="addOnDetailsDecimal"]`).type('5526.03');
      cy.get(`[data-cy="addOnDetailsDecimal"]`).should('have.value', '5526.03');

      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('not.be.checked');
      cy.get(`[data-cy="addOnDetailsBoolean"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('be.checked');

      cy.get(`[data-cy="addOnDetailsBigInt"]`).type('7366');
      cy.get(`[data-cy="addOnDetailsBigInt"]`).should('have.value', '7366');

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
