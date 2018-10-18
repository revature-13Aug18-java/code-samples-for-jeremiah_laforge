import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import {FormValidationService} from "../form-validation.service";
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from '../session.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {
  profileForm: FormGroup;
  response: any;
  userUpdated = false;
  userUpdatedFailed = false;
  isLoggedIn: Observable<boolean>;
  userId:string;


  onSubmit() {
    console.log(this.profileForm.valid);

    //action="http://localhost:8082/lesoptimates.project2.backend/newUser"

    const headers = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
      }), withCredentials: true
    };
    if (this.profileForm.valid) {
      //let body = this.profileForm.value;
      let body = `id=${this.userId}name=${this.profileForm.value.name}&userName=${this.profileForm.value.userName}&email=${this.profileForm.value.email}
    &pswd=${this.profileForm.value.password}`;
      console.log("body is : " + body);

      this.httpClient.post("http://localhost:8082/lesoptimates.project2.backend/updateUser", body, headers)
        .subscribe(success => console.log('success'),
          error => {
            console.log(error.status);;
            if (error.status == 200) {
              this.userUpdated = true;
            } else {
              this.userUpdatedFailed = true;
            }
          });
    }else{
      console.log("invalid form. can not submit")
    }
  }

  constructor(fb: FormBuilder, private fv: FormValidationService,
    private router: Router, private httpClient: HttpClient, private sessionService: SessionService) {
    this.profileForm = fb.group({
      userName: [null, Validators.required],
      name: [null, Validators.required],
      email: [null, Validators.email],
      password: [null, Validators.required],
      testPassword: [null, Validators.required]
    }, {
        validator: fv.ValidateForm
      })
  }



  ngOnInit() {
    this.isLoggedIn = this.sessionService.isLoggedIn;

    this.sessionService.getSessionPromise().then((data:any) => {
      if(data!=null){
        this.userId = data.userId;
        console.log(this.userId);
      }
    });

  }

}
