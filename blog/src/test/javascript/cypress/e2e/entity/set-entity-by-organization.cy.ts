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
  const setEntityByOrganizationPageUrl = '/blog/set-entity-by-organization';
  const setEntityByOrganizationPageUrlPattern = new RegExp('/blog/set-entity-by-organization(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const setEntityByOrganizationSample = {};

  let setEntityByOrganization;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/set-entity-by-organizations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/set-entity-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/set-entity-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (setEntityByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/set-entity-by-organizations/${setEntityByOrganization.organizationId}`,
      }).then(() => {
        setEntityByOrganization = undefined;
      });
    }
  });

  it('SetEntityByOrganizations menu should load SetEntityByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/set-entity-by-organization');
    cy.wait('@entitiesRequest').then(({ response }) => {
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
        cy.wait('@entitiesRequest');
      });

      it('should load create SetEntityByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/set-entity-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('SetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/set-entity-by-organizations',
          body: setEntityByOrganizationSample,
        }).then(({ body }) => {
          setEntityByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/set-entity-by-organizations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [setEntityByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(setEntityByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SetEntityByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('setEntityByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit SetEntityByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit SetEntityByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SetEntityByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
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
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', setEntityByOrganizationPageUrlPattern);

        setEntityByOrganization = undefined;
      });
    });
  });

  describe('new SetEntityByOrganization page', () => {
    beforeEach(() => {
      cy.visit(`${setEntityByOrganizationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SetEntityByOrganization');
    });

    it('should create an instance of SetEntityByOrganization', () => {
      cy.get(`[data-cy="tags"]`).type('peaceful beside');
      cy.get(`[data-cy="tags"]`).should('have.value', 'peaceful beside');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        setEntityByOrganization = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', setEntityByOrganizationPageUrlPattern);
    });
  });
});
