import { Component } from '@angular/core';
import {RolesService} from "../services/roles.service";
import {Subject, Observable} from 'rxjs';
import {TeachersService} from "../services/teachers.service";

@Component({
  moduleId: module.id,
  selector: 'roles-component',
  templateUrl: './templates/roles.component.html',
  styleUrls: ['./styles/roles.component.css']
})
export class RolesComponent  {
  roles: any[] = [];
  selectedRole: any = null;

  constructor(private rolesService: RolesService, private teachersService: TeachersService){
    rolesService.rolesSubject.subscribe((roles) => {
      this.roles = roles;
    });
    teachersService.teachersSubject.subscribe((teachers) => {
      console.log(teachers);
    });
  }

  selectRole(roleId){
    this.selectedRole = this.roles.find((role) => role.roleId == roleId);
  }
}
