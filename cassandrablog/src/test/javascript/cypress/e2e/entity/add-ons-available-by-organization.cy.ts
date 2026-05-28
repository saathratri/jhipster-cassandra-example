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
  const addOnsAvailableByOrganizationSample = {
    compositeId: {
      organizationId: '00000000-0000-4000-8000-000000000001',
      entityType: 'sample-entityType-1',
      entityId: '00000000-0000-4000-8000-000000000001',
      addOnId: '00000000-0000-4000-8000-000000000001',
    },
  };

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
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/add-ons-available-by-organizations\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/add-ons-available-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/add-ons-available-by-organizations/*/*/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (addOnsAvailableByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/add-ons-available-by-organizations/${addOnsAvailableByOrganization.compositeId.organizationId}/${addOnsAvailableByOrganization.compositeId.entityType}/${addOnsAvailableByOrganization.compositeId.entityId}/${addOnsAvailableByOrganization.compositeId.addOnId}`,
      }).then(() => {
        addOnsAvailableByOrganization = undefined;
      });
    }
  });

  it('AddOnsAvailableByOrganizations menu should load AddOnsAvailableByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/add-ons-available-by-organization');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
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
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create AddOnsAvailableByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/add-ons-available-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('AddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
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
              url: /^\/services\/cassandrablog\/api\/add-ons-available-by-organizations\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [addOnsAvailableByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(addOnsAvailableByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details AddOnsAvailableByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('addOnsAvailableByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit AddOnsAvailableByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit AddOnsAvailableByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AddOnsAvailableByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
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
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
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
      cy.get(`[data-cy="organizationId"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="organizationId"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="entityType"]`).type('midst scarily majority');
      cy.get(`[data-cy="entityType"]`).should('have.value', 'midst scarily majority');

      cy.get(`[data-cy="entityId"]`).type('bc5d5225-cfcd-4e6a-bc1e-d8ff5d84d115');
      cy.get(`[data-cy="entityId"]`).invoke('val').should('match', new RegExp('bc5d5225-cfcd-4e6a-bc1e-d8ff5d84d115'));

      cy.get(`[data-cy="addOnId"]`).type('f8ccc258-d399-4fb1-b548-7255b74385b9');
      cy.get(`[data-cy="addOnId"]`).invoke('val').should('match', new RegExp('f8ccc258-d399-4fb1-b548-7255b74385b9'));

      cy.get(`[data-cy="addOnType"]`).type('ah vulgarise clearly');
      cy.get(`[data-cy="addOnType"]`).should('have.value', 'ah vulgarise clearly');
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        addOnsAvailableByOrganization = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', addOnsAvailableByOrganizationPageUrlPattern);
    });

    it('should accept input on the addOnDetailsText MAP widget add row', () => {
      cy.get(`[data-cy="addOnDetailsText-add-key"]`).type('sample-key');
      cy.get(`[data-cy="addOnDetailsText-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="addOnDetailsText-add-value"]`).type('sample-value');
      cy.get(`[data-cy="addOnDetailsText-add-value"]`).should('have.value', 'sample-value');
      cy.get(`[data-cy="addOnDetailsText-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the addOnDetailsDecimal MAP widget add row', () => {
      cy.get(`[data-cy="addOnDetailsDecimal-add-key"]`).type('sample-key');
      cy.get(`[data-cy="addOnDetailsDecimal-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="addOnDetailsDecimal-add-value"]`).type('1001');
      cy.get(`[data-cy="addOnDetailsDecimal-add-value"]`).should('have.value', '1001');
      cy.get(`[data-cy="addOnDetailsDecimal-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the addOnDetailsBoolean MAP<BOOLEAN> widget add row', () => {
      cy.get(`[data-cy="addOnDetailsBoolean-add-key"]`).type('sample-key');
      cy.get(`[data-cy="addOnDetailsBoolean-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="addOnDetailsBoolean-add-toggle"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean-add-button"]`).should('not.be.disabled');
    });

    it('should accept input on the addOnDetailsBigInt MAP<BIGINT/DATETIME> widget add row', () => {
      cy.get(`[data-cy="addOnDetailsBigInt-add-key"]`).type('sample-key');
      cy.get(`[data-cy="addOnDetailsBigInt-add-key"]`).should('have.value', 'sample-key');
      cy.get(`[data-cy="addOnDetailsBigInt-add-button"]`).should('exist');
    });
  });
});
