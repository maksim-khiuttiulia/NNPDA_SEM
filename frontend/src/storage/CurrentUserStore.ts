import User from "../entities/User";
import {action, observable} from "mobx";
import {Location} from "../entities/Location";

class CurrentUserStore {
    @observable
    private _user: User | undefined

    @observable
    private _locations: Location[] | undefined

    get user(): User | undefined {
        return this._user;
    }

    @action
    set user(value: User | undefined) {
        this._user = value;
    }

    get locations(): Location[] {
        return this._locations || [];
    }

    set locations(value: Location[]) {
        this._locations = value;
    }
}

export default new CurrentUserStore()