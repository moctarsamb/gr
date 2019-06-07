/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { JointureComponentsPage, JointureDeleteDialog, JointureUpdatePage } from './jointure.page-object';

const expect = chai.expect;

describe('Jointure e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let jointureUpdatePage: JointureUpdatePage;
    let jointureComponentsPage: JointureComponentsPage;
    let jointureDeleteDialog: JointureDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Jointures', async () => {
        await navBarPage.goToEntity('jointure');
        jointureComponentsPage = new JointureComponentsPage();
        await browser.wait(ec.visibilityOf(jointureComponentsPage.title), 5000);
        expect(await jointureComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.jointure.home.title');
    });

    it('should load create Jointure page', async () => {
        await jointureComponentsPage.clickOnCreateButton();
        jointureUpdatePage = new JointureUpdatePage();
        expect(await jointureUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.jointure.home.createOrEditLabel');
        await jointureUpdatePage.cancel();
    });

    it('should create and save Jointures', async () => {
        const nbButtonsBeforeCreate = await jointureComponentsPage.countDeleteButtons();

        await jointureComponentsPage.clickOnCreateButton();
        await promise.all([
            jointureUpdatePage.critereSelectLastOption(),
            jointureUpdatePage.fluxSelectLastOption(),
            jointureUpdatePage.typeJointureSelectLastOption()
        ]);
        await jointureUpdatePage.save();
        expect(await jointureUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await jointureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Jointure', async () => {
        const nbButtonsBeforeDelete = await jointureComponentsPage.countDeleteButtons();
        await jointureComponentsPage.clickOnLastDeleteButton();

        jointureDeleteDialog = new JointureDeleteDialog();
        expect(await jointureDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.jointure.delete.question');
        await jointureDeleteDialog.clickOnConfirmButton();

        expect(await jointureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
