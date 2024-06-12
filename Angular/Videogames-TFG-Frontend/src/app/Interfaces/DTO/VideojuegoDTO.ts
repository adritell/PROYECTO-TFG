export interface VideojuegoDTO {
    id: number;
    nombre: string;
    genero: string;
    descripcion: string;
    anioPublicacion: number;
    precio: number;
    calificacionPorEdades: string;
    publicador: string;
    plataformas: string[];
    imagePath: string;
    isFavorite?: boolean;  // Añadido
 }
