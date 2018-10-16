import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Recipe } from '../../models/Recipe';

@Component({
  selector: 'app-todays-specials',
  templateUrl: './todays-specials.component.html',
  styleUrls: ['./todays-specials.component.css']
})
export class TodaysSpecialsComponent implements OnInit {

  recipe: Recipe;
  response:any;
  //key1: d163d5127df3dc954c85893da2da4f2e 
  //key2: 1f15f4b4b0d1f534478e53ac0e52e894
  //key3: 2ae4418069c000dc8c72aebc231c2e2d
  //key4: 1802aecacbc0e5758def8391efa70956

  constructor(private httpClient: HttpClient) { }

  showRecipes(str: string) {
    let url = "https://www.food2fork.com/api/search?key=1802aecacbc0e5758def8391efa70956&q=" + str;
    console.log(url);
    this.httpClient.get(url)
      .subscribe( (data:any) => {
        for(let i;i<4;i++){
          this.response = data.recipes;
        console.log(this.response);
        }
        console.log("json: " + this.response);
        
      });
  }


  ngOnInit() {
    this.showRecipes('jalapeno')
  }

}
