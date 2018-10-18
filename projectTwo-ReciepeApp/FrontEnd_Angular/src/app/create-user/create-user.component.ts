import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import {FormValidationService} from "../form-validation.service";
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  profileForm: FormGroup;
  response: any;
  htmlButton = true;
  bsButton = false;
  userCreated = false;
  userCreatedFailed = false;


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
      let body = `name=${this.profileForm.value.name}&userName=${this.profileForm.value.userName}&email=${this.profileForm.value.email}
    &pswd=${this.profileForm.value.password}`;
      console.log("body is : " + body);

      this.httpClient.post("http://localhost:8080/lesoptimates.project2.backend/newUser", body, headers).subscribe(success => console.log('success'),
          error => {
            console.log(error.status);
            if (error.status == 200) {
              this.userCreated = true;
            } else {
              this.userCreatedFailed = true;
            }
          });
    }else{
      console.log("invalid form. can not submit")
    }
  }

  constructor(fb: FormBuilder, private fv: FormValidationService,
    private router: Router, private httpClient: HttpClient) {
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
  }

}
