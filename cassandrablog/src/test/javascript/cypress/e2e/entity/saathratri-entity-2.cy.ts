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

describe('SaathratriEntity2 e2e test', () => {
  const saathratriEntity2PageUrl = '/cassandrablog/saathratri-entity-2';
  const saathratriEntity2PageUrlPattern = new RegExp('/cassandrablog/saathratri-entity-2(\\?.*)?$');
  let username: string;
  let password: string;
  const saathratriEntity2Sample = {};

  let saathratriEntity2;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/cassandrablog/api/saathratri-entity-2-s+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/saathratri-entity-2-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/saathratri-entity-2-s/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (saathratriEntity2) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/saathratri-entity-2-s/${saathratriEntity2.entityTypeId}`,
      }).then(() => {
        saathratriEntity2 = undefined;
      });
    }
  });

  it('SaathratriEntity2s menu should load SaathratriEntity2s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/saathratri-entity-2');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SaathratriEntity2').should('exist');
    cy.url().should('match', saathratriEntity2PageUrlPattern);
  });

  describe('SaathratriEntity2 page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(saathratriEntity2PageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SaathratriEntity2 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/saathratri-entity-2/new$'));
        cy.getEntityCreateUpdateHeading('SaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/saathratri-entity-2-s',
          body: saathratriEntity2Sample,
        }).then(({ body }) => {
          saathratriEntity2 = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/cassandrablog/api/saathratri-entity-2-s+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [saathratriEntity2],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(saathratriEntity2PageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SaathratriEntity2 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('saathratriEntity2');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity2 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity2 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity2');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);
      });

      it('last delete button click should delete instance of SaathratriEntity2', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('saathratriEntity2').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity2PageUrlPattern);

        saathratriEntity2 = undefined;
      });
    });
  });

  describe('new SaathratriEntity2 page', () => {
    beforeEach(() => {
      cy.visit(saathratriEntity2PageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SaathratriEntity2');
    });

    it('should create an instance of SaathratriEntity2', () => {
      cy.get(`[data-cy="yearOfDateAdded"]`).type('4014');
      cy.get(`[data-cy="yearOfDateAdded"]`).should('have.value', '4014');

      cy.get(`[data-cy="arrivalDate"]`).type('3619');
      cy.get(`[data-cy="arrivalDate"]`).should('have.value', '3619');

      cy.get(`[data-cy="blogId"]`).type('39e61ae5-1757-4f8a-8ecf-6f2ee1a651f3');
      cy.get(`[data-cy="blogId"]`).invoke('val').should('match', new RegExp('39e61ae5-1757-4f8a-8ecf-6f2ee1a651f3'));

      cy.get(`[data-cy="entityName"]`).type('urgently versus or');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'urgently versus or');

      cy.get(`[data-cy="entityDescription"]`).type('pointed');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'pointed');

      cy.get(`[data-cy="entityCost"]`).type('13622.79');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '13622.79');

      cy.get(`[data-cy="departureDate"]`).type('10708');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '10708');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        saathratriEntity2 = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', saathratriEntity2PageUrlPattern);
    });
  });
});
