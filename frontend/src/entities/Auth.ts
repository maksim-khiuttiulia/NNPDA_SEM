export default interface Auth {
    username: string,
    password: string,
    roles: UserRole,
    token: string
}

export enum UserRole {
    ADMIN = "ADMIN",
    USER = "USER"
}
