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

describe('Post e2e test', () => {
  const postPageUrl = '/cassandrablog/post';
  const postPageUrlPattern = new RegExp('/cassandrablog/post(\\?.*)?$');
  let username: string;
  let password: string;
  const postSample = {
    compositeId: { createdDate: 1001, addedDateTime: 1001, postId: '00000000-0000-4000-8000-000000000001' },
    title: 'contrail energetically retrospectivity',
    content: 'even windy',
  };

  let post;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/posts\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/posts').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/posts/*/*/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (post) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/posts/${post.compositeId.createdDate}/${post.compositeId.addedDateTime}/${post.compositeId.postId}`,
      }).then(() => {
        post = undefined;
      });
    }
  });

  it('Posts menu should load Posts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/post');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Post').should('exist');
    cy.url().should('match', postPageUrlPattern);
  });

  describe('Post page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(postPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create Post page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/post/new$'));
        cy.getEntityCreateUpdateHeading('Post');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', postPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/posts',
          body: postSample,
        }).then(({ body }) => {
          post = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/posts\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [post],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(postPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details Post page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('post');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', postPageUrlPattern);
      });

      it('edit button click should load edit Post page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Post');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', postPageUrlPattern);
      });

      it('edit button click should load edit Post page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Post');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', postPageUrlPattern);
      });

      it('last delete button click should delete instance of Post', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('post').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', postPageUrlPattern);

        post = undefined;
      });
    });
  });

  describe('new Post page', () => {
    beforeEach(() => {
      cy.visit(postPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Post');
    });

    it('should create an instance of Post', () => {
      cy.get(`[data-cy="createdDate"]`).type('1001');
      cy.get(`[data-cy="createdDate"]`).should('have.value', '1001');

      cy.get(`[data-cy="postId"]`).type('c2a66e78-54b4-422f-be8c-37350746ce3e');
      cy.get(`[data-cy="postId"]`).invoke('val').should('match', new RegExp('c2a66e78-54b4-422f-be8c-37350746ce3e'));

      cy.get(`[data-cy="title"]`).type('loudly');
      cy.get(`[data-cy="title"]`).should('have.value', 'loudly');

      cy.get(`[data-cy="content"]`).type('representation owlishly woot');
      cy.get(`[data-cy="content"]`).should('have.value', 'representation owlishly woot');

      cy.get(`[data-cy="sentDate"]`).type('13556');
      cy.get(`[data-cy="sentDate"]`).should('have.value', '13556');

      cy.get(`app-date-time[fieldName="addedDateTime"]`).parent().contains('button', 'Generate').click();
      cy.get(`app-date-time[fieldName="publishedDateTime"]`).parent().contains('button', 'Generate').click();

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        post = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', postPageUrlPattern);
    });
  });
});
