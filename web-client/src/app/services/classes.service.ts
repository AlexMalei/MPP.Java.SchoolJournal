import {Injectable, Inject} from "@angular/core";
import {ReplaySubject} from "rxjs";
import {Subject, Observable} from 'rxjs';
import {Http, URLSearchParams} from "@angular/http";
import {APP_CONFIG} from "../configs/app.config";
import {AuthService} from "./auth.service";

const _ = require('lodash');

@Injectable()
export class ClassesService {

  classes = [];
  classesSubject: ReplaySubject<any> = new ReplaySubject(1);

  constructor(@Inject(APP_CONFIG) private config: any,
              private http: Http,
              private authService: AuthService){
    this.fetchClasses();
  }

  fetchClasses() {
    return new Promise((resolve, reject) => {
      let params = new URLSearchParams();
      params.append('token', this.authService.token);
      this.http.get(`${this.config.apiEndpoint}/classes`, {search: params})
        .map(res => res.json())
        .catch((err) => {
          reject(err);
          return Observable.throw(err);
        })
        .subscribe((classes) => {
          classes = _.sortBy(classes, (c) => `${c.number} ${c.letterMark}`);
          this.classes = classes;
          this.classesSubject.next(classes);
          resolve(classes);
        });
    });
  }

  createClass(clazz) {
    return new Promise((resolve, reject) => {
      let params = new URLSearchParams();
      params.append('token', this.authService.token);
      this.http.post(`${this.config.apiEndpoint}/classes`, clazz, {search: params})
        .map(res => res.json())
        .catch((err) => {
          reject(err);
          return Observable.throw(err);
        })
        .subscribe((clazz) => {
          resolve(clazz);
        });
    });
  }

  deleteClass(classId) {
    return new Promise((resolve, reject) => {
      let params = new URLSearchParams();
      params.append('token', this.authService.token);
      this.http.delete(`${this.config.apiEndpoint}/classes/${classId}`, {search: params})
        .catch((err) => {
          reject(err);
          return Observable.throw(err);
        })
        .subscribe((data) => {
          resolve({});
        });
    });
  }

}
