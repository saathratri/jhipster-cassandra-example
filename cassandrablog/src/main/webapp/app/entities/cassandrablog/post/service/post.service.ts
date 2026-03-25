import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IPost, IPostId, NewPost } from '../post.model';
export type PartialUpdatePost = Partial<IPost> & Pick<IPost, 'compositeId'>;

type RestOf<T extends IPost | NewPost> = Omit<T, 'createdDate' | 'addedDateTime' | 'publishedDateTime' | 'sentDate'> & {
  compositeId: {
    createdDate?: number | null;
    addedDateTime?: number | null;
  };
  publishedDateTime?: number | null;
  sentDate?: number | null;
};

export type RestPost = RestOf<IPost>;

export type NewRestPost = RestOf<NewPost>;

export type PartialUpdateRestPost = RestOf<PartialUpdatePost>;

export type EntityResponseType = HttpResponse<IPost>;
export type EntityArrayResponseType = HttpResponse<IPost[]>;

@Injectable()
export class PostsService {
  readonly postsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(undefined);
  readonly postsResource = httpResource<RestPost[]>(() => {
    const params = this.postsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of post that have been fetched.
   */
  readonly posts = computed(() =>
    (this.postsResource.hasValue() ? this.postsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/posts', 'cassandrablog');

  protected convertValueFromServer(restPost: RestPost): IPost {
    return {
      ...restPost,
      compositeId: {
        createdDate: restPost.compositeId.createdDate ? dayjs(restPost.compositeId.createdDate) : null,
        addedDateTime: restPost.compositeId.addedDateTime ? dayjs(restPost.compositeId.addedDateTime) : null,

        postId: restPost.compositeId.postId,
      },
      publishedDateTime: restPost.publishedDateTime ? dayjs(restPost.publishedDateTime) : null,
      sentDate: restPost.sentDate ? dayjs(restPost.sentDate) : null,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class PostService extends PostsService {
  protected readonly http = inject(HttpClient);

  create(post: NewPost): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(post);
    return this.http.post<RestPost>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(post: IPost): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(post);
    return this.http
      .put<RestPost>(
        `${this.resourceUrl}/${copy.compositeId.createdDate}/${copy.compositeId.addedDateTime}/${post.compositeId.postId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(post: PartialUpdatePost): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(post);
    return this.http
      .patch<RestPost>(
        `${this.resourceUrl}/${copy.compositeId.createdDate}/${copy.compositeId.addedDateTime}/${post.compositeId.postId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(createdDate: number, addedDateTime: number, postId: string): Observable<EntityResponseType> {
    const data = { createdDate, addedDateTime, postId };
    return this.http
      .get<RestPost>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPost[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDatePageable(createdDate: number, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', String(createdDate));
    return this.http
      .get<RestPost[]>(`${this.resourceUrl}/find-all-by-composite-id-created-date-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', String(createdDate));
    options = options.set('addedDateTime', String(addedDateTime));
    return this.http
      .get<
        RestPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostIdPageable(
    createdDate: number,
    addedDateTime: number,
    postId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', String(createdDate));
    options = options.set('addedDateTime', String(addedDateTime));
    options = options.set('postId', String(postId));
    return this.http
      .get<
        RestPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-and-composite-id-post-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', String(createdDate));
    options = options.set('addedDateTime', String(addedDateTime));
    return this.http
      .get<
        RestPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', String(createdDate));
    options = options.set('addedDateTime', String(addedDateTime));
    return this.http
      .get<
        RestPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', String(createdDate));
    options = options.set('addedDateTime', String(addedDateTime));
    return this.http
      .get<
        RestPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', String(createdDate));
    options = options.set('addedDateTime', String(addedDateTime));
    return this.http
      .get<
        RestPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
    createdDate: number,
    addedDateTime: number,
    postId: string,
  ): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('createdDate', String(createdDate));
    options = options.set('addedDateTime', String(addedDateTime));
    options = options.set('postId', String(postId));
    return this.http
      .get<RestPost>(`${this.resourceUrl}/find-by-composite-id-created-date-and-composite-id-added-date-time-and-composite-id-post-id`, {
        params: options,
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  delete(post: IPost): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(post);
    return this.http.delete(
      `${this.resourceUrl}/${copy.compositeId.createdDate}/${copy.compositeId.addedDateTime}/${post.compositeId.postId}`,
      { observe: 'response' },
    );
  }

  getPostIdentifier(post: Pick<IPost, 'compositeId'>): IPostId {
    return post.compositeId;
  }
  comparePost(o1: Pick<IPost, 'compositeId'> | null, o2: Pick<IPost, 'compositeId'> | null): boolean {
    return o1 && o2 ? this.getPostIdentifier(o1) === this.getPostIdentifier(o2) : o1 === o2;
  }
  addPostToCollectionIfMissing<Type extends Pick<IPost, 'compositeId'>>(
    postCollection: Type[],
    ...postsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const posts: Type[] = postsToCheck.filter(isPresent);
    if (posts.length > 0) {
      const postCollectionIdentifiers = postCollection.map(postItem => this.getPostIdentifier(postItem));
      const postsToAdd = posts.filter(postItem => {
        const postIdentifier = this.getPostIdentifier(postItem);
        if (postCollectionIdentifiers.includes(postIdentifier)) {
          return false;
        }
        postCollectionIdentifiers.push(postIdentifier);
        return true;
      });
      return [...postsToAdd, ...postCollection];
    }
    return postCollection;
  }

  protected convertValueFromClient<T extends IPost | NewPost | PartialUpdatePost>(post: T): RestOf<T> {
    return {
      ...post,
      compositeId: {
        ...post.compositeId,
        createdDate: post.compositeId.createdDate ? post.compositeId.createdDate.valueOf() : null,

        addedDateTime: post.compositeId.addedDateTime ? post.compositeId.addedDateTime.valueOf() : null,
      },
      publishedDateTime: post.publishedDateTime ? post.publishedDateTime.valueOf() : null,
      sentDate: post.sentDate ? post.sentDate.valueOf() : null,
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestPost>): HttpResponse<IPost> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPost[]>): HttpResponse<IPost[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
