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
  const addOnsAvailableByOrganizationPageUrl = '/cassandrablog/add-ons-available-by-organization';
  const addOnsAvailableByOrganizationPageUrlPattern = new RegExp('/cassandrablog/add-ons-available-by-organization(\\?.*)?$');
  let username: string;
  let password: string;
  const addOnsAvailableByOrganizationSample = {};

  let addOnsAvailableByOrganization;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/cassandrablog/api/add-ons-available-by-organizations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/add-ons-available-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/add-ons-available-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (addOnsAvailableByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/add-ons-available-by-organizations/${addOnsAvailableByOrganization.organizationId}`,
      }).then(() => {
        addOnsAvailableByOrganization = undefined;
      });
    }
  });

  it('AddOnsAvailableByOrganizations menu should load AddOnsAvailableByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/add-ons-available-by-organization');
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
        cy.url().should('match', new RegExp('/cassandrablog/add-ons-available-by-organization/new$'));
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
          url: '/services/cassandrablog/api/add-ons-available-by-organizations',
          body: addOnsAvailableByOrganizationSample,
        }).then(({ body }) => {
          addOnsAvailableByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/cassandrablog/api/add-ons-available-by-organizations+(?*|)',
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
      cy.visit(addOnsAvailableByOrganizationPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AddOnsAvailableByOrganization');
    });

    it('should create an instance of AddOnsAvailableByOrganization', () => {
      cy.get(`[data-cy="entityType"]`).type('midst scarily majority');
      cy.get(`[data-cy="entityType"]`).should('have.value', 'midst scarily majority');

      cy.get(`[data-cy="entityId"]`).type('bc5d5225-cfcd-4e6a-bc1e-d8ff5d84d115');
      cy.get(`[data-cy="entityId"]`).invoke('val').should('match', new RegExp('bc5d5225-cfcd-4e6a-bc1e-d8ff5d84d115'));

      cy.get(`[data-cy="addOnId"]`).type('f8ccc258-d399-4fb1-b548-7255b74385b9');
      cy.get(`[data-cy="addOnId"]`).invoke('val').should('match', new RegExp('f8ccc258-d399-4fb1-b548-7255b74385b9'));

      cy.get(`[data-cy="addOnType"]`).type('ah vulgarise clearly');
      cy.get(`[data-cy="addOnType"]`).should('have.value', 'ah vulgarise clearly');

      cy.get(`[data-cy="addOnDetailsText"]`).type('tail colorize without');
      cy.get(`[data-cy="addOnDetailsText"]`).should('have.value', 'tail colorize without');

      cy.get(`[data-cy="addOnDetailsDecimal"]`).type('13484.47');
      cy.get(`[data-cy="addOnDetailsDecimal"]`).should('have.value', '13484.47');

      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('not.be.checked');
      cy.get(`[data-cy="addOnDetailsBoolean"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('be.checked');

      cy.get(`[data-cy="addOnDetailsBigInt"]`).type('18260');
      cy.get(`[data-cy="addOnDetailsBigInt"]`).should('have.value', '18260');

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
