/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MonitorComponentsPage, MonitorDeleteDialog, MonitorUpdatePage } from './monitor.page-object';

const expect = chai.expect;

describe('Monitor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let monitorUpdatePage: MonitorUpdatePage;
    let monitorComponentsPage: MonitorComponentsPage;
    let monitorDeleteDialog: MonitorDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Monitors', async () => {
        await navBarPage.goToEntity('monitor');
        monitorComponentsPage = new MonitorComponentsPage();
        await browser.wait(ec.visibilityOf(monitorComponentsPage.title), 5000);
        expect(await monitorComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.monitor.home.title');
    });

    it('should load create Monitor page', async () => {
        await monitorComponentsPage.clickOnCreateButton();
        monitorUpdatePage = new MonitorUpdatePage();
        expect(await monitorUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.monitor.home.createOrEditLabel');
        await monitorUpdatePage.cancel();
    });

    it('should create and save Monitors', async () => {
        const nbButtonsBeforeCreate = await monitorComponentsPage.countDeleteButtons();

        await monitorComponentsPage.clickOnCreateButton();
        await promise.all([
            monitorUpdatePage.typeSelectLastOption(),
            monitorUpdatePage.colonneSelectLastOption(),
            monitorUpdatePage.fonctionSelectLastOption()
        ]);
        await monitorUpdatePage.save();
        expect(await monitorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await monitorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Monitor', async () => {
        const nbButtonsBeforeDelete = await monitorComponentsPage.countDeleteButtons();
        await monitorComponentsPage.clickOnLastDeleteButton();

        monitorDeleteDialog = new MonitorDeleteDialog();
        expect(await monitorDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.monitor.delete.question');
        await monitorDeleteDialog.clickOnConfirmButton();

        expect(await monitorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
