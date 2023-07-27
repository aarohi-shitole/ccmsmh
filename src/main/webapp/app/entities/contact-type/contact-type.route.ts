import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContactType, ContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from './contact-type.service';
import { ContactTypeComponent } from './contact-type.component';
import { ContactTypeDetailComponent } from './contact-type-detail.component';
import { ContactTypeUpdateComponent } from './contact-type-update.component';

@Injectable({ providedIn: 'root' })
export class ContactTypeResolve implements Resolve<IContactType> {
  constructor(private service: ContactTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contactType: HttpResponse<ContactType>) => {
          if (contactType.body) {
            return of(contactType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactType());
  }
}

export const contactTypeRoute: Routes = [
  {
    path: '',
    component: ContactTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ContactTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactTypeDetailComponent,
    resolve: {
      contactType: ContactTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ContactTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactTypeUpdateComponent,
    resolve: {
      contactType: ContactTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ContactTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactTypeUpdateComponent,
    resolve: {
      contactType: ContactTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ContactTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
