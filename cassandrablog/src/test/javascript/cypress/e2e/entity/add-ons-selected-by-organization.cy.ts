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
  const addOnsSelectedByOrganizationPageUrl = '/cassandrablog/add-ons-selected-by-organization';
  const addOnsSelectedByOrganizationPageUrlPattern = new RegExp('/cassandrablog/add-ons-selected-by-organization(\\?.*)?$');
  let username: string;
  let password: string;
  const addOnsSelectedByOrganizationSample = {};

  let addOnsSelectedByOrganization;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/cassandrablog/api/add-ons-selected-by-organizations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/add-ons-selected-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/add-ons-selected-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (addOnsSelectedByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/add-ons-selected-by-organizations/${addOnsSelectedByOrganization.organizationId}`,
      }).then(() => {
        addOnsSelectedByOrganization = undefined;
      });
    }
  });

  it('AddOnsSelectedByOrganizations menu should load AddOnsSelectedByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/add-ons-selected-by-organization');
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
        cy.url().should('match', new RegExp('/cassandrablog/add-ons-selected-by-organization/new$'));
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
          url: '/services/cassandrablog/api/add-ons-selected-by-organizations',
          body: addOnsSelectedByOrganizationSample,
        }).then(({ body }) => {
          addOnsSelectedByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/cassandrablog/api/add-ons-selected-by-organizations+(?*|)',
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
      cy.visit(addOnsSelectedByOrganizationPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AddOnsSelectedByOrganization');
    });

    it('should create an instance of AddOnsSelectedByOrganization', () => {
      cy.get(`[data-cy="arrivalDate"]`).type('29638');
      cy.get(`[data-cy="arrivalDate"]`).should('have.value', '29638');

      cy.get(`[data-cy="accountNumber"]`).type('weep');
      cy.get(`[data-cy="accountNumber"]`).should('have.value', 'weep');

      cy.get(`[data-cy="createdTimeId"]`).type('dcf1b50e-9318-4f23-90b2-e431ef138d39');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('dcf1b50e-9318-4f23-90b2-e431ef138d39'));

      cy.get(`[data-cy="departureDate"]`).type('30028');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '30028');

      cy.get(`[data-cy="customerId"]`).type('f43aca66-bd29-4674-afbc-1206dff5d87e');
      cy.get(`[data-cy="customerId"]`).invoke('val').should('match', new RegExp('f43aca66-bd29-4674-afbc-1206dff5d87e'));

      cy.get(`[data-cy="customerFirstName"]`).type('stale pfft thongs');
      cy.get(`[data-cy="customerFirstName"]`).should('have.value', 'stale pfft thongs');

      cy.get(`[data-cy="customerLastName"]`).type('triumphantly nudge');
      cy.get(`[data-cy="customerLastName"]`).should('have.value', 'triumphantly nudge');

      cy.get(`[data-cy="customerUpdatedEmail"]`).type('self-reliant phooey');
      cy.get(`[data-cy="customerUpdatedEmail"]`).should('have.value', 'self-reliant phooey');

      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).type('ugly');
      cy.get(`[data-cy="customerUpdatedPhoneNumber"]`).should('have.value', 'ugly');

      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).type('criminal psst gracious');
      cy.get(`[data-cy="customerEstimatedArrivalTime"]`).should('have.value', 'criminal psst gracious');

      cy.get(`[data-cy="tinyUrlShortCode"]`).type('whoa meh');
      cy.get(`[data-cy="tinyUrlShortCode"]`).should('have.value', 'whoa meh');

      cy.get(`[data-cy="addOnDetailsText"]`).type('unnaturally');
      cy.get(`[data-cy="addOnDetailsText"]`).should('have.value', 'unnaturally');

      cy.get(`[data-cy="addOnDetailsDecimal"]`).type('25973.7');
      cy.get(`[data-cy="addOnDetailsDecimal"]`).should('have.value', '25973.7');

      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('not.be.checked');
      cy.get(`[data-cy="addOnDetailsBoolean"]`).click();
      cy.get(`[data-cy="addOnDetailsBoolean"]`).should('be.checked');

      cy.get(`[data-cy="addOnDetailsBigInt"]`).type('24715');
      cy.get(`[data-cy="addOnDetailsBigInt"]`).should('have.value', '24715');

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
