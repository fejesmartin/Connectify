import { Injectable } from '@angular/core';
import axios from 'axios'
import { baseURL } from '../assets/baseURL';

@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  constructor() {
    axios.defaults.baseURL = baseURL;
    axios.defaults.headers.post["Content-Type"] = 'application/json';
   }

   request(method: string, url: string, data: any): Promise<any>{
    return axios({
      method: method,
      url: url,
      data: data
    });
   }
}
