import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertService } from '../_services';


@Component({ selector: 'footer', template: '<div id="smsfooter"><p>Survey Management System 2021. Version - 0.0.1-SNAPSHOT</p></div>' })
export class FooterComponent {
    private subscription: Subscription;
    message: any;

    constructor(private alertService: AlertService) { }
}