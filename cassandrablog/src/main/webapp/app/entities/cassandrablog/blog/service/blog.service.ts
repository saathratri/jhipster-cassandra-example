import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IBlog, IBlogId, NewBlog } from '../blog.model';
export type PartialUpdateBlog = Partial<IBlog> & Pick<IBlog, 'compositeId'>;

export type EntityResponseType = HttpResponse<IBlog>;
export type EntityArrayResponseType = HttpResponse<IBlog[]>;

@Injectable()
export class BlogsService {
  readonly blogsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(undefined);
  readonly blogsResource = httpResource<IBlog[]>(() => {
    const params = this.blogsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of blog that have been fetched.
   */
  readonly blogs = computed(() => (this.blogsResource.hasValue() ? this.blogsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/blogs', 'cassandrablog');
}

@Injectable({ providedIn: 'root' })
export class BlogService extends BlogsService {
  protected readonly http = inject(HttpClient);

  create(blog: NewBlog): Observable<EntityResponseType> {
    return this.http.post<IBlog>(this.resourceUrl, blog, { observe: 'response' });
  }

  update(blog: IBlog): Observable<EntityResponseType> {
    return this.http.put<IBlog>(`${this.resourceUrl}/${blog.compositeId.category}/${blog.compositeId.blogId}`, blog, {
      observe: 'response',
    });
  }

  partialUpdate(blog: PartialUpdateBlog): Observable<EntityResponseType> {
    return this.http.patch<IBlog>(`${this.resourceUrl}/${blog.compositeId.category}/${blog.compositeId.blogId}`, blog, {
      observe: 'response',
    });
  }

  find(category: string, blogId: string): Observable<EntityResponseType> {
    const data = { category, blogId };
    return this.http.get<IBlog>(`${this.resourceUrl}/get`, { params: data, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBlog[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(blog: IBlog): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${blog.compositeId.category}/${blog.compositeId.blogId}`, { observe: 'response' });
  }

  getBlogIdentifier(blog: Pick<IBlog, 'compositeId'>): IBlogId {
    return blog.compositeId;
  }
  compareBlog(o1: Pick<IBlog, 'compositeId'> | null, o2: Pick<IBlog, 'compositeId'> | null): boolean {
    return o1 && o2 ? this.getBlogIdentifier(o1) === this.getBlogIdentifier(o2) : o1 === o2;
  }
  addBlogToCollectionIfMissing<Type extends Pick<IBlog, 'compositeId'>>(
    blogCollection: Type[],
    ...blogsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const blogs: Type[] = blogsToCheck.filter(isPresent);
    if (blogs.length > 0) {
      const blogCollectionIdentifiers = blogCollection.map(blogItem => this.getBlogIdentifier(blogItem));
      const blogsToAdd = blogs.filter(blogItem => {
        const blogIdentifier = this.getBlogIdentifier(blogItem);
        if (blogCollectionIdentifiers.includes(blogIdentifier)) {
          return false;
        }
        blogCollectionIdentifiers.push(blogIdentifier);
        return true;
      });
      return [...blogsToAdd, ...blogCollection];
    }
    return blogCollection;
  }
}
