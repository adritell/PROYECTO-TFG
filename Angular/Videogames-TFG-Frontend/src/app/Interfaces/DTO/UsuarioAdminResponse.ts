export interface UsuarioAdminResponse { 
    id: number;
    nombre: string;
    apellidos: string;
    email: string;
    activo: boolean;
    roles: string[];
}
