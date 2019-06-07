/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TypeJointureComponentsPage, TypeJointureDeleteDialog, TypeJointureUpdatePage } from './type-jointure.page-object';

const expect = chai.expect;

describe('TypeJointure e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let typeJointureUpdatePage: TypeJointureUpdatePage;
    let typeJointureComponentsPage: TypeJointureComponentsPage;
    let typeJointureDeleteDialog: TypeJointureDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TypeJointures', async () => {
        await navBarPage.goToEntity('type-jointure');
        typeJointureComponentsPage = new TypeJointureComponentsPage();
        await browser.wait(ec.visibilityOf(typeJointureComponentsPage.title), 5000);
        expect(await typeJointureComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.typeJointure.home.title');
    });

    it('should load create TypeJointure page', async () => {
        await typeJointureComponentsPage.clickOnCreateButton();
        typeJointureUpdatePage = new TypeJointureUpdatePage();
        expect(await typeJointureUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.typeJointure.home.createOrEditLabel'
        );
        await typeJointureUpdatePage.cancel();
    });

    it('should create and save TypeJointures', async () => {
        const nbButtonsBeforeCreate = await typeJointureComponentsPage.countDeleteButtons();

        await typeJointureComponentsPage.clickOnCreateButton();
        await promise.all([typeJointureUpdatePage.setTypeInput('type')]);
        expect(await typeJointureUpdatePage.getTypeInput()).to.eq('type');
        await typeJointureUpdatePage.save();
        expect(await typeJointureUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await typeJointureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TypeJointure', async () => {
        const nbButtonsBeforeDelete = await typeJointureComponentsPage.countDeleteButtons();
        await typeJointureComponentsPage.clickOnLastDeleteButton();

        typeJointureDeleteDialog = new TypeJointureDeleteDialog();
        expect(await typeJointureDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.typeJointure.delete.question');
        await typeJointureDeleteDialog.clickOnConfirmButton();

        expect(await typeJointureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
