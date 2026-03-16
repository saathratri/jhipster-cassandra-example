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

describe('LandingPageByOrganization e2e test', () => {
  const landingPageByOrganizationPageUrl = '/cassandrablog/landing-page-by-organization';
  const landingPageByOrganizationPageUrlPattern = new RegExp('/cassandrablog/landing-page-by-organization(\\?.*)?$');
  let username: string;
  let password: string;
  const landingPageByOrganizationSample = {};

  let landingPageByOrganization;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/cassandrablog/api/landing-page-by-organizations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/landing-page-by-organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/landing-page-by-organizations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (landingPageByOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/landing-page-by-organizations/${landingPageByOrganization.organizationId}`,
      }).then(() => {
        landingPageByOrganization = undefined;
      });
    }
  });

  it('LandingPageByOrganizations menu should load LandingPageByOrganizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/landing-page-by-organization');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('LandingPageByOrganization').should('exist');
    cy.url().should('match', landingPageByOrganizationPageUrlPattern);
  });

  describe('LandingPageByOrganization page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(landingPageByOrganizationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create LandingPageByOrganization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/landing-page-by-organization/new$'));
        cy.getEntityCreateUpdateHeading('LandingPageByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', landingPageByOrganizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/landing-page-by-organizations',
          body: landingPageByOrganizationSample,
        }).then(({ body }) => {
          landingPageByOrganization = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/cassandrablog/api/landing-page-by-organizations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [landingPageByOrganization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(landingPageByOrganizationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details LandingPageByOrganization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('landingPageByOrganization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', landingPageByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit LandingPageByOrganization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LandingPageByOrganization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', landingPageByOrganizationPageUrlPattern);
      });

      it('edit button click should load edit LandingPageByOrganization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LandingPageByOrganization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', landingPageByOrganizationPageUrlPattern);
      });

      it('last delete button click should delete instance of LandingPageByOrganization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('landingPageByOrganization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', landingPageByOrganizationPageUrlPattern);

        landingPageByOrganization = undefined;
      });
    });
  });

  describe('new LandingPageByOrganization page', () => {
    beforeEach(() => {
      cy.visit(landingPageByOrganizationPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('LandingPageByOrganization');
    });

    it('should create an instance of LandingPageByOrganization', () => {
      cy.get(`[data-cy="detailsText"]`).type('lecture');
      cy.get(`[data-cy="detailsText"]`).should('have.value', 'lecture');

      cy.get(`[data-cy="detailsDecimal"]`).type('6787.75');
      cy.get(`[data-cy="detailsDecimal"]`).should('have.value', '6787.75');

      cy.get(`[data-cy="detailsBoolean"]`).should('not.be.checked');
      cy.get(`[data-cy="detailsBoolean"]`).click();
      cy.get(`[data-cy="detailsBoolean"]`).should('be.checked');

      cy.get(`[data-cy="detailsBigInt"]`).type('6081');
      cy.get(`[data-cy="detailsBigInt"]`).should('have.value', '6081');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        landingPageByOrganization = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', landingPageByOrganizationPageUrlPattern);
    });
  });
});
