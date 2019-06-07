import { Component, OnInit } from '@angular/core';
import { now } from 'moment';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit {
    currentYear: String;

    ngOnInit(): void {
        this.currentYear = new Date(now()).getFullYear().toString();
    }
}
