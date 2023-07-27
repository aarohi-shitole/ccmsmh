import { HttpParams } from '@angular/common/http';

export interface Pagination {
  page: number;
  size: number;
  sort: string[];
}

export interface Search {
  query: string;
}

export interface SearchWithPagination extends Search, Pagination {}

export const createRequestOption = (req?: any): HttpParams => {
  let options: HttpParams = new HttpParams();

  if (req) {
    Object.keys(req).forEach(key => {
      if (key !== 'sort') {
        options = options.set(key, req[key]);
      }
    });

    if (req.sort) {
      req.sort.forEach((val: string) => {
        options = options.append('sort', val);
      });
    }

    if (req.page === undefined) {
      options = options.set('page', '0');
      options = options.set('size', '1000');
    }
  }else{
    options = options.set('sort', 'id,asc');
    options = options.set('page', '0');
    options = options.set('size', '1000');
  }

  return options;
};
