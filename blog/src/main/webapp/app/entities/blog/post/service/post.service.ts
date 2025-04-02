import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
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

@Injectable({ providedIn: 'root' })
export class PostService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/posts', 'blog');

  create(post: NewPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(post);
    return this.http.post<RestPost>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(post: IPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(post);
    return this.http
      .put<RestPost>(
        `${this.resourceUrl}/${copy.compositeId.createdDate}/${copy.compositeId.addedDateTime}/${post.compositeId.postId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(post: PartialUpdatePost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(post);
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

  delete(post: IPost): Observable<HttpResponse<{}>> {
    const copy = this.convertDateFromClient(post);
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

  protected convertDateFromClient<T extends IPost | NewPost | PartialUpdatePost>(post: T): RestOf<T> {
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

  protected convertDateFromServer(restPost: RestPost): IPost {
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

  protected convertResponseFromServer(res: HttpResponse<RestPost>): HttpResponse<IPost> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPost[]>): HttpResponse<IPost[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
