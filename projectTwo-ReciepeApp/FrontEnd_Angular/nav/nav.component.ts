import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SessionService } from '../session.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  
  isLoggedIn: Observable<boolean>;

  constructor(private route: ActivatedRoute,
    private router: Router, private sessionService: SessionService) { }


  // didSearch = false;
  recipeSearch: string;
  getSearchVal(value: string) { this.recipeSearch = value; }
  response:any;
  showRecipe() {
    console.log(this.recipeSearch);
    this.router.navigate(['/search'], { queryParams: { str:this.recipeSearch } });

    // let url = "https://www.food2fork.com/api/search?key=2ae4418069c000dc8c72aebc231c2e2d";
    // //&q=chicken%20breast question string format
    // this.httpClient.get("https://www.food2fork.com/api/search?key=2ae4418069c000dc8c72aebc231c2e2d")
    //   .subscribe( (data:any) => {
    //     this.response = data.recipes;
    //     console.log(this.response);
    //   });
  }

  ngOnInit() {
    this.isLoggedIn = this.sessionService.isLoggedIn;
  }

}
