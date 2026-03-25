import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ITag, NewTag } from '../tag.model';
export type PartialUpdateTag = Partial<ITag> & Pick<ITag, 'id'>;

export type EntityResponseType = HttpResponse<ITag>;
export type EntityArrayResponseType = HttpResponse<ITag[]>;

@Injectable()
export class TagsService {
  readonly tagsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(undefined);
  readonly tagsResource = httpResource<ITag[]>(() => {
    const params = this.tagsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of tag that have been fetched.
   */
  readonly tags = computed(() => (this.tagsResource.hasValue() ? this.tagsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/tags', 'cassandrablog');
}

@Injectable({ providedIn: 'root' })
export class TagService extends TagsService {
  protected readonly http = inject(HttpClient);

  create(tag: NewTag): Observable<EntityResponseType> {
    return this.http.post<ITag>(this.resourceUrl, tag, { observe: 'response' });
  }

  update(tag: ITag): Observable<EntityResponseType> {
    return this.http.put<ITag>(`${this.resourceUrl}/${this.getTagIdentifier(tag)}`, tag, { observe: 'response' });
  }

  partialUpdate(tag: PartialUpdateTag): Observable<EntityResponseType> {
    return this.http.patch<ITag>(`${this.resourceUrl}/${this.getTagIdentifier(tag)}`, tag, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITag[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTagIdentifier(tag: Pick<ITag, 'id'>): string {
    return tag.id;
  }

  compareTag(o1: Pick<ITag, 'id'> | null, o2: Pick<ITag, 'id'> | null): boolean {
    return o1 && o2 ? this.getTagIdentifier(o1) === this.getTagIdentifier(o2) : o1 === o2;
  }

  addTagToCollectionIfMissing<Type extends Pick<ITag, 'id'>>(tagCollection: Type[], ...tagsToCheck: (Type | null | undefined)[]): Type[] {
    const tags: Type[] = tagsToCheck.filter(isPresent);
    if (tags.length > 0) {
      const tagCollectionIdentifiers = tagCollection.map(tagItem => this.getTagIdentifier(tagItem));
      const tagsToAdd = tags.filter(tagItem => {
        const tagIdentifier = this.getTagIdentifier(tagItem);
        if (tagCollectionIdentifiers.includes(tagIdentifier)) {
          return false;
        }
        tagCollectionIdentifiers.push(tagIdentifier);
        return true;
      });
      return [...tagsToAdd, ...tagCollection];
    }
    return tagCollection;
  }

  aiSearch(query: string, limit: number, fields?: string[]): Observable<ITag[]> {
    const params: { [key: string]: string | string[] } = { query, limit: String(limit) };
    if (fields && fields.length > 0) {
      params.fields = fields.join(',');
    }
    return this.http.get<ITag[]>(`${this.resourceUrl}/ai-search`, {
      params,
    });
  }
}
