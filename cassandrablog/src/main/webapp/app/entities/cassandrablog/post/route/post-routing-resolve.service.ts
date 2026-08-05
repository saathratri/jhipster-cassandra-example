import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPost } from '../post.model';
import { PostService } from '../service/post.service';

const postResolve = (route: ActivatedRouteSnapshot): Observable<null | IPost> => {
  const { createdDate } = route.params;
  const { addedDateTime } = route.params;
  const { postId } = route.params;

  if (createdDate && addedDateTime && postId) {
    return inject(PostService)
      .find(createdDate, addedDateTime, postId)
      .pipe(
        mergeMap((post: HttpResponse<IPost>) => {
          if (post.body) {
            return of(post.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default postResolve;
