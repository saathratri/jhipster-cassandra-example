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
  const saathratriEntityPageUrl = '/cassandrablog/saathratri-entity';
  const saathratriEntityPageUrlPattern = new RegExp('/cassandrablog/saathratri-entity(\\?.*)?$');
  let username: string;
  let password: string;
  const saathratriEntitySample = {};

  let saathratriEntity;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/cassandrablog/api/saathratri-entities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/saathratri-entities').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/saathratri-entities/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (saathratriEntity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/saathratri-entities/${saathratriEntity.entityId}`,
      }).then(() => {
        saathratriEntity = undefined;
      });
    }
  });

  it('SaathratriEntities menu should load SaathratriEntities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/saathratri-entity');
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
        cy.url().should('match', new RegExp('/cassandrablog/saathratri-entity/new$'));
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
          url: '/services/cassandrablog/api/saathratri-entities',
          body: saathratriEntitySample,
        }).then(({ body }) => {
          saathratriEntity = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/cassandrablog/api/saathratri-entities+(?*|)',
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
      cy.visit(saathratriEntityPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SaathratriEntity');
    });

    it('should create an instance of SaathratriEntity', () => {
      cy.get(`[data-cy="entityName"]`).type('huzzah');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'huzzah');

      cy.get(`[data-cy="entityDescription"]`).type('nucleotidase hence');
      cy.get(`[data-cy="entityDescription"]`).should('have.value', 'nucleotidase hence');

      cy.get(`[data-cy="entityCost"]`).type('17922.77');
      cy.get(`[data-cy="entityCost"]`).should('have.value', '17922.77');

      cy.get(`[data-cy="createdId"]`).type('30634437-859b-412f-b11b-3dca0fd0a82a');
      cy.get(`[data-cy="createdId"]`).invoke('val').should('match', new RegExp('30634437-859b-412f-b11b-3dca0fd0a82a'));

      cy.get(`[data-cy="createdTimeId"]`).type('68805aae-e81e-4a67-99e6-8884798a20a4');
      cy.get(`[data-cy="createdTimeId"]`).invoke('val').should('match', new RegExp('68805aae-e81e-4a67-99e6-8884798a20a4'));

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
