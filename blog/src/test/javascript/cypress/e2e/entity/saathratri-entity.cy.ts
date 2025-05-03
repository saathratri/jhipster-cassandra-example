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

describe('SaathratriEntity e2e test', () => {
  const saathratriEntityPageUrl = '/blog/saathratri-entity';
  const saathratriEntityPageUrlPattern = new RegExp('/blog/saathratri-entity(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const saathratriEntitySample = {};

  let saathratriEntity;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/blog/api/saathratri-entities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/blog/api/saathratri-entities').as('postEntityRequest');
    cy.intercept('DELETE', '/services/blog/api/saathratri-entities/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (saathratriEntity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/blog/api/saathratri-entities/${saathratriEntity.entityId}`,
      }).then(() => {
        saathratriEntity = undefined;
      });
    }
  });

  it('SaathratriEntities menu should load SaathratriEntities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blog/saathratri-entity');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SaathratriEntity').should('exist');
    cy.url().should('match', saathratriEntityPageUrlPattern);
  });

  describe('SaathratriEntity page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(saathratriEntityPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SaathratriEntity page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blog/saathratri-entity/new$'));
        cy.getEntityCreateUpdateHeading('SaathratriEntity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/blog/api/saathratri-entities',
          body: saathratriEntitySample,
        }).then(({ body }) => {
          saathratriEntity = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/blog/api/saathratri-entities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [saathratriEntity],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(saathratriEntityPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SaathratriEntity page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('saathratriEntity');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntityPageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntityPageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntityPageUrlPattern);
      });

      it('last delete button click should delete instance of SaathratriEntity', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('saathratriEntity').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntityPageUrlPattern);

        saathratriEntity = undefined;
      });
    });
  });

  describe('new SaathratriEntity page', () => {
    beforeEach(() => {
      cy.visit(`${saathratriEntityPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SaathratriEntity');
    });

    it('should create an instance of SaathratriEntity', () => {
      cy.get(`[data-cy="entityName"]`).type('lone harp barring');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'lone harp barring');

      cy.get(`[data-cy="entityDescription"]`).type('even scared blah');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'even scared blah');

      cy.get(`[data-cy="entityCost"]`).type('28732.52');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '28732.52');

      cy.get(`[data-cy="createdId"]`).type('a74f787a-2422-4e3c-96c2-0d97c1e4d3e2');
      cy.get(`[data-cy="createdId"]`).invoke('val').should('match', new RegExp('a74f787a-2422-4e3c-96c2-0d97c1e4d3e2'));

      cy.get(`[data-cy="createdTimeId"]`).type('8608c202-0ed9-4784-a1b0-ab204e5295cf');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('8608c202-0ed9-4784-a1b0-ab204e5295cf'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        saathratriEntity = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', saathratriEntityPageUrlPattern);
    });
  });
});
