export interface User{
    id: number,
    username: string,
    email: string,
    authorities: string[],
    enabled: boolean,
    userId: number,
    accountNonExpired: boolean,
    accountNonLocked: boolean,
    credentialsNonExpired: boolean
}