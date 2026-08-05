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

describe('Tag e2e test', () => {
  const tagPageUrl = '/cassandrablog/tag';
  const tagPageUrlPattern = new RegExp('/cassandrablog/tag(\\?.*)?$');
  let username: string;
  let password: string;
  const tagSample = { id: '00000000-0000-4000-8000-000000000001', name: 'illiterate' };

  let tag;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/tags\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/tags').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/tags/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (tag) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/tags/${tag.id}`,
      }).then(() => {
        tag = undefined;
      });
    }
  });

  it('Tags menu should load Tags page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/tag');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Tag').should('exist');
    cy.url().should('match', tagPageUrlPattern);
  });

  describe('Tag page', () => {
    it('should have translated page title', () => {
      cy.visit(tagPageUrl);
      cy.getEntityHeading('Tag').should('not.contain', 'cassandrablogApp.cassandrablogTag.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(tagPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create Tag page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/tag/new$'));
        cy.getEntityCreateUpdateHeading('Tag');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tagPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/tags',
          body: tagSample,
        }).then(({ body }) => {
          tag = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/tags\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [tag],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(tagPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details Tag page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('tag');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tagPageUrlPattern);
      });

      it('edit button click should load edit Tag page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Tag');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tagPageUrlPattern);
      });

      it('edit button click should load edit Tag page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Tag');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tagPageUrlPattern);
      });

      it('last delete button click should delete instance of Tag', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('tag').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tagPageUrlPattern);

        tag = undefined;
      });
    });
  });

  describe('new Tag page', () => {
    beforeEach(() => {
      cy.visit(tagPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Tag');
    });

    it('should create an instance of Tag', () => {
      cy.get(`[data-cy="id"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="id"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="name"]`).type('necklace outside');
      cy.get(`[data-cy="name"]`).should('have.value', 'necklace outside');

      cy.get(`[data-cy="description"]`).type('honestly by');
      cy.get(`[data-cy="description"]`).should('have.value', 'honestly by');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        tag = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', tagPageUrlPattern);
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(tagPageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="id-generate"]`).click();
    cy.get(`[data-cy="id"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="id-reset"]`).click();
    cy.get(`[data-cy="id"]`).should('have.value', '');
  });

  it('should run an AI semantic search', () => {
    cy.intercept('GET', /\/api\/tags\/ai-search/).as('aiSearchRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/tag');
    cy.wait('@entitiesRequest', { timeout: 30000 });
    cy.get('#aiSearchQuery').type('semantic query');
    cy.get('form[name="aiSearchForm"] button.btn-warning').click();
    cy.wait('@aiSearchRequest', { timeout: 30000 }).its('response.statusCode').should('eq', 200);
  });

  it('should generate embeddings on create and regenerate on update (fake embedding model)', function () {
    cy.env(['fakeEmbeddings']).then(({ fakeEmbeddings }) => {
      if (!fakeEmbeddings) this.skip();
    });
    const createdText = `cypress embed ${Date.now()}`;
    const updatedText = `cypress reembed ${Date.now()}`;
    cy.authenticatedRequest({ method: 'POST', url: '/services/cassandrablog/api/tags', body: { ...tagSample, name: createdText } }).then(
      ({ body }) => {
        tag = body;
        // Embedding created on insert: the vector column is populated (exposed via the DTO).
        cy.authenticatedRequest({ method: 'GET', url: `/services/cassandrablog/api/tags/${tag.id}` }).then(({ body: created }) => {
          expect(created.nameEmbedding, 'embedding generated on create').to.exist;
          const embeddingBefore = JSON.stringify(created.nameEmbedding);
          // The ANN search finds the new row for its exact text.
          cy.visit('/');
          cy.clickOnEntityMenuItem('cassandrablog/tag');
          cy.wait('@entitiesRequest', { timeout: 30000 });
          cy.intercept('GET', /\/api\/tags\/ai-search/).as('aiSearchCreated');
          cy.get('#aiSearchQuery').clear().type(createdText);
          cy.get('form[name="aiSearchForm"] button.btn-warning').click();
          cy.wait('@aiSearchCreated', { timeout: 30000 }).then(({ response }) => {
            expect(response.statusCode).to.eq(200);
            expect(response.body.map(row => row.id)).to.include(tag.id);
          });
          // Update the source text; the stored vector must be REGENERATED (value changes).
          cy.authenticatedRequest({
            method: 'PUT',
            url: `/services/cassandrablog/api/tags/${tag.id}`,
            body: { ...created, name: updatedText },
          });
          cy.authenticatedRequest({ method: 'GET', url: `/services/cassandrablog/api/tags/${tag.id}` }).then(({ body: updated }) => {
            expect(updated.nameEmbedding, 'embedding regenerated on update').to.exist;
            expect(JSON.stringify(updated.nameEmbedding)).to.not.eq(embeddingBefore);
          });
        });
      },
    );
  });
});
