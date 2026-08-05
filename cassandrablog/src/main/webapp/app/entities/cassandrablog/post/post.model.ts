/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import dayjs from 'dayjs/esm';

export interface IPost {
  compositeId: IPostId;
  title?: string | null;
  content?: string | null;
  publishedDateTime?: dayjs.Dayjs | null;
  sentDate?: dayjs.Dayjs | null;
}
export interface IPostId {
  createdDate: dayjs.Dayjs | null;
  addedDateTime: dayjs.Dayjs | null;
  postId: string | null;
}

export type NewPost = Omit<IPost, 'compositeId'> & { compositeId: IPostId };
