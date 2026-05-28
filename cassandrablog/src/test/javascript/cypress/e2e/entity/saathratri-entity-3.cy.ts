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

describe('SaathratriEntity3 e2e test', () => {
  const saathratriEntity3PageUrl = '/cassandrablog/saathratri-entity-3';
  const saathratriEntity3PageUrlPattern = new RegExp('/cassandrablog/saathratri-entity-3(\\?.*)?$');
  let username: string;
  let password: string;
  const saathratriEntity3Sample = {
    compositeId: { entityType: 'sample-entityType-1', createdTimeId: '00000000-0000-4000-8000-000000000001' },
  };

  let saathratriEntity3;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/saathratri-entity-3-s\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/saathratri-entity-3-s').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/saathratri-entity-3-s/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (saathratriEntity3) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/saathratri-entity-3-s/${saathratriEntity3.compositeId.entityType}/${saathratriEntity3.compositeId.createdTimeId}`,
      }).then(() => {
        saathratriEntity3 = undefined;
      });
    }
  });

  it('SaathratriEntity3s menu should load SaathratriEntity3s page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/saathratri-entity-3');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SaathratriEntity3').should('exist');
    cy.url().should('match', saathratriEntity3PageUrlPattern);
  });

  describe('SaathratriEntity3 page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(saathratriEntity3PageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create SaathratriEntity3 page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/saathratri-entity-3/new$'));
        cy.getEntityCreateUpdateHeading('SaathratriEntity3');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity3PageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/saathratri-entity-3-s',
          body: saathratriEntity3Sample,
        }).then(({ body }) => {
          saathratriEntity3 = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/saathratri-entity-3-s\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [saathratriEntity3],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(saathratriEntity3PageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details SaathratriEntity3 page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('saathratriEntity3');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity3PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity3 page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity3');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity3PageUrlPattern);
      });

      it('edit button click should load edit SaathratriEntity3 page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SaathratriEntity3');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity3PageUrlPattern);
      });

      it('last delete button click should delete instance of SaathratriEntity3', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('saathratriEntity3').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', saathratriEntity3PageUrlPattern);

        saathratriEntity3 = undefined;
      });
    });
  });

  describe('new SaathratriEntity3 page', () => {
    beforeEach(() => {
      cy.visit(saathratriEntity3PageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SaathratriEntity3');
    });

    it('should create an instance of SaathratriEntity3', () => {
      cy.get(`[data-cy="entityType"]`).type('1dc81d79-b38e-4129-9f38-54d48cd77b53');
      cy.get(`[data-cy="entityType"]`).should('have.value', '1dc81d79-b38e-4129-9f38-54d48cd77b53');

      cy.get(`[data-cy="createdTimeId"]`).type('80f92053-15fd-4316-85a6-653ebe913a18');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('80f92053-15fd-4316-85a6-653ebe913a18'));

      cy.get(`[data-cy="entityName"]`).type('a');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'a');

      cy.get(`[data-cy="entityDescription"]`).type('firsthand');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'firsthand');

      cy.get(`[data-cy="entityCost"]`).type('19238.72');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '19238.72');

      cy.get(`[data-cy="departureDate"]`).type('14623');
      cy.get(`[data-cy="departureDate"]`).should('have.value', '14623');
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        saathratriEntity3 = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', saathratriEntity3PageUrlPattern);
    });

    it('should accept input on the tags SET widget add row', () => {
      cy.get(`[data-cy="tags-add-value"]`).type('sample-tags-1');
      cy.get(`[data-cy="tags-add-value"]`).should('have.value', 'sample-tags-1');
      cy.get(`[data-cy="tags-add-button"]`).should('not.be.disabled');
    });
  });
});
