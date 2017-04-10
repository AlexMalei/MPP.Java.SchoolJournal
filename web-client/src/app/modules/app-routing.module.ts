import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RolesComponent} from "../components/roles.component";
import {ProfileComponent} from "../components/profile.component";
import {LoginComponent} from "../components/login.component";
import {ScheduleComponent} from "../components/schedule.component";
import {MarksComponent} from "../components/marks.component";
import {FullScheduleComponent} from "../components/full-schedule.component";
import {ClassesComponent} from "../components/classes.component";
import {SubjectsComponent} from "../components/subjects.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'profile', component: ProfileComponent },
  { path: 'profile/:id', component: ProfileComponent },
  { path: 'login', component: LoginComponent },
  { path: 'schedule', component: ScheduleComponent },
  { path: 'marks', component: MarksComponent },
  { path: 'fullSchedule', component: FullScheduleComponent },
  { path: 'classes', component: ClassesComponent },
  { path: 'subjects', component: SubjectsComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
