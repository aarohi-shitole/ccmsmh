import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'state',
        loadChildren: () => import('./state/state.module').then(m => m.CovidCareStateModule),
      },
      {
        path: 'district',
        loadChildren: () => import('./district/district.module').then(m => m.CovidCareDistrictModule),
      },
      {
        path: 'taluka',
        loadChildren: () => import('./taluka/taluka.module').then(m => m.CovidCareTalukaModule),
      },
      {
        path: 'city',
        loadChildren: () => import('./city/city.module').then(m => m.CovidCareCityModule),
      },
      {
        path: 'municipal-corp',
        loadChildren: () => import('./municipal-corp/municipal-corp.module').then(m => m.CovidCareMunicipalCorpModule),
      },
      {
        path: 'hospital',
        loadChildren: () => import('./hospital/hospital.module').then(m => m.CovidCareHospitalModule),
      },
      {
        path: 'hospital-type',
        loadChildren: () => import('./hospital-type/hospital-type.module').then(m => m.CovidCareHospitalTypeModule),
      },
      {
        path: 'bed-type',
        loadChildren: () => import('./bed-type/bed-type.module').then(m => m.CovidCareBedTypeModule),
      },
      {
        path: 'bed-inventory',
        loadChildren: () => import('./bed-inventory/bed-inventory.module').then(m => m.CovidCareBedInventoryModule),
      },
      {
        path: 'user-access',
        loadChildren: () => import('./user-access/user-access.module').then(m => m.CovidCareUserAccessModule),
      },
      {
        path: 'security-user',
        loadChildren: () => import('./security-user/security-user.module').then(m => m.CovidCareSecurityUserModule),
      },
      {
        path: 'security-role',
        loadChildren: () => import('./security-role/security-role.module').then(m => m.CovidCareSecurityRoleModule),
      },
      {
        path: 'security-permission',
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.CovidCareSecurityPermissionModule),
      },
      {
        path: 'inventory-type',
        loadChildren: () => import('./inventory-type/inventory-type.module').then(m => m.CovidCareInventoryTypeModule),
      },
      {
        path: 'inventory-master',
        loadChildren: () => import('./inventory-master/inventory-master.module').then(m => m.CovidCareInventoryMasterModule),
      },
      {
        path: 'supplier',
        loadChildren: () => import('./supplier/supplier.module').then(m => m.CovidCareSupplierModule),
      },
      {
        path: 'inventory',
        loadChildren: () => import('./inventory/inventory.module').then(m => m.CovidCareInventoryModule),
      },
      {
        path: 'inventory-used',
        loadChildren: () => import('./inventory-used/inventory-used.module').then(m => m.CovidCareInventoryUsedModule),
      },
      {
        path: 'transactions',
        loadChildren: () => import('./transactions/transactions.module').then(m => m.CovidCareTransactionsModule),
      },
      {
        path: 'division',
        loadChildren: () => import('./division/division.module').then(m => m.CovidCareDivisionModule),
      },
      {
        path: 'bed-transactions',
        loadChildren: () => import('./bed-transactions/bed-transactions.module').then(m => m.CovidCareBedTransactionsModule),
      },
      {
        path: 'trip',
        loadChildren: () => import('./trip/trip.module').then(m => m.CovidCareTripModule),
      },
      {
        path: 'trip-details',
        loadChildren: () => import('./trip-details/trip-details.module').then(m => m.CovidCareTripDetailsModule),
      },
      {
        path: 'audit-type',
        loadChildren: () => import('./audit-type/audit-type.module').then(m => m.CovidCareAuditTypeModule),
      },
      {
        path: 'audit-system',
        loadChildren: () => import('./audit-system/audit-system.module').then(m => m.CovidCareAuditSystemModule),
      },
      {
        path: 'contact-type',
        loadChildren: () => import('./contact-type/contact-type.module').then(m => m.CovidCareContactTypeModule),
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.CovidCareContactModule),
      },
      {
        path: 'patient-info',
        loadChildren: () => import('./patient-info/patient-info.module').then(m => m.CovidCarePatientInfoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CovidCareEntityModule {}
