import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import {
  trigger,
  state,
  style,
  transition,
  animate
} from '@angular/animations';
import { Recipe } from '../../models/Recipe';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from '../session.service';
import { RecipeService } from '../recipe.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  animations: [
    trigger('saveToFavorites', [
      state('saved', style({
        backgroundColor: 'red'
      })),
      state('unsaved', style({
        backgroundColor: 'white'
      })),
      transition('saved => unsaved', [animate('1s')]),
      transition('unsaved => saved', [animate('1s')]),
    ]),
  ],
})
export class SearchComponent implements OnInit {
  recipe: Recipe;
  userId: string;
  //key1: d163d5127df3dc954c85893da2da4f2e 
  //key2: 1f15f4b4b0d1f534478e53ac0e52e894
  //key3: 2ae4418069c000dc8c72aebc231c2e2d

  constructor(private httpClient: HttpClient, private route: ActivatedRoute,
    private router: Router, private sessionService: SessionService,
    private recipeService: RecipeService) { }

  response:any;
  search: any;
  searchStr: any;

  showRecipes(str: string) {
    console.log("showrecipescalled: " + str)
    let url = "https://www.food2fork.com/api/search?key=1f15f4b4b0d1f534478e53ac0e52e894&q=" + str;
    console.log(url);
    this.httpClient.get(url)
      .subscribe( (data:any) => {
        this.response = data.recipes;
        console.log(this.response);
      });
  }


  getSession() {
    this.httpClient.get("http://localhost:8082/lesoptimates.project2.backend/session", {withCredentials:true})
      .subscribe( (data:any) => {
        if(data!=null){
          this.userId = data.userId;
          console.log(this.userId);
        }
      });
  }
  
  showRecipe() {
    let response:any;
    let recipes, savedRecipes: any[];
    let currentUser:string = this.sessionService.getCurrentUserId();

    if(currentUser){
      savedRecipes = this.recipeService.returnUserRecipes(currentUser);
    }

    this.httpClient.get("https://www.food2fork.com/api/search?key=d163d5127df3dc954c85893da2da4f2e")
      .subscribe( (data:any) => {
        response = data.recipes;
        console.log(response);
      });

      for(let r of response){
        for(let s of savedRecipes){
          //TODO:if()
        }


      }
  }

  saveRecipe(userId,json){ 
    const headers = {
      headers: new HttpHeaders({
        'Content-Type':  'application/x-www-form-urlencoded'
      })
    };
   
    console.log(this.recipe);
    console.log("userId: " + userId)
    let body = `userId=${userId}&JSON=${JSON.stringify(json)}`;

    this.httpClient.post("http://localhost:8082/lesoptimates.project2.backend/recipes/save",body,  headers )
    .subscribe( (data:any) => {
  
    });

    this.showRecipe();
  }

  ngOnInit() {
    this.search = this.route
    .queryParams
    .subscribe(params => {
      this.showRecipes(params.str);
    });
  }

}
