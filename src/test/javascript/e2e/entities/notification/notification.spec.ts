/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NotificationComponentsPage, NotificationDeleteDialog, NotificationUpdatePage } from './notification.page-object';

const expect = chai.expect;

describe('Notification e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let notificationUpdatePage: NotificationUpdatePage;
    let notificationComponentsPage: NotificationComponentsPage;
    let notificationDeleteDialog: NotificationDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Notifications', async () => {
        await navBarPage.goToEntity('notification');
        notificationComponentsPage = new NotificationComponentsPage();
        await browser.wait(ec.visibilityOf(notificationComponentsPage.title), 5000);
        expect(await notificationComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.notification.home.title');
    });

    it('should load create Notification page', async () => {
        await notificationComponentsPage.clickOnCreateButton();
        notificationUpdatePage = new NotificationUpdatePage();
        expect(await notificationUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.notification.home.createOrEditLabel'
        );
        await notificationUpdatePage.cancel();
    });

    it('should create and save Notifications', async () => {
        const nbButtonsBeforeCreate = await notificationComponentsPage.countDeleteButtons();

        await notificationComponentsPage.clickOnCreateButton();
        await promise.all([
            notificationUpdatePage.setLibelleInput('libelle'),
            notificationUpdatePage.setDescriptionInput('description'),
            notificationUpdatePage.utilisateurSelectLastOption()
        ]);
        expect(await notificationUpdatePage.getLibelleInput()).to.eq('libelle');
        expect(await notificationUpdatePage.getDescriptionInput()).to.eq('description');
        const selectedEstOuvert = notificationUpdatePage.getEstOuvertInput();
        if (await selectedEstOuvert.isSelected()) {
            await notificationUpdatePage.getEstOuvertInput().click();
            expect(await notificationUpdatePage.getEstOuvertInput().isSelected()).to.be.false;
        } else {
            await notificationUpdatePage.getEstOuvertInput().click();
            expect(await notificationUpdatePage.getEstOuvertInput().isSelected()).to.be.true;
        }
        await notificationUpdatePage.save();
        expect(await notificationUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Notification', async () => {
        const nbButtonsBeforeDelete = await notificationComponentsPage.countDeleteButtons();
        await notificationComponentsPage.clickOnLastDeleteButton();

        notificationDeleteDialog = new NotificationDeleteDialog();
        expect(await notificationDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.notification.delete.question');
        await notificationDeleteDialog.clickOnConfirmButton();

        expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
