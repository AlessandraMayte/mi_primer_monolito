document.addEventListener('DOMContentLoaded', function() {
    // --- Elementos de Modales Existentes ---
    var editOwnerModal = document.getElementById('editOwnerModal');
    var deleteOwnerButton = document.getElementById('deleteOwnerButton');
    var editPetModal = document.getElementById('editPetModal');
    var deletePetButton = document.getElementById('deletePetButton');

    // --- NUEVOS Elementos del Modal de Agregar Propietario ---
    var addOwnerModal = document.getElementById('addOwnerModal');
    var createOwnerButton = document.getElementById('createOwnerButton');

    // --- Lógica del Modal de Edición de Propietario (sin cambios) ---
    if (editOwnerModal) {
        editOwnerModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;
            var ownerId = button.getAttribute('data-owner-id');
            var ownerNombre = button.getAttribute('data-owner-nombre');
            var ownerApellido = button.getAttribute('data-owner-apellido');
            var ownerTelefono = button.getAttribute('data-owner-telefono');
            var ownerDireccion = button.getAttribute('data-owner-direccion');

            var modalIdInput = editOwnerModal.querySelector('#ownerId');
            var modalNombreInput = editOwnerModal.querySelector('#ownerNombre');
            var modalApellidoInput = editOwnerModal.querySelector('#ownerApellido');
            var modalTelefonoInput = editOwnerModal.querySelector('#ownerTelefono');
            var modalDireccionInput = editOwnerModal.querySelector('#ownerDireccion');

            if (modalIdInput) modalIdInput.value = ownerId;
            if (modalNombreInput) modalNombreInput.value = ownerNombre;
            if (modalApellidoInput) modalApellidoInput.value = ownerApellido;
            if (modalTelefonoInput) modalTelefonoInput.value = ownerTelefono;
            if (modalDireccionInput) modalDireccionInput.value = ownerDireccion;

            var modalTitle = editOwnerModal.querySelector('.modal-title');
            if (modalTitle) modalTitle.textContent = 'Editar Propietario: ' + ownerNombre + ' ' + ownerApellido;
        });

        var saveOwnerChangesButton = document.getElementById('saveOwnerChanges');
        if (saveOwnerChangesButton) {
            saveOwnerChangesButton.addEventListener('click', function() {
                var ownerId = document.getElementById('ownerId').value;
                var nombre = document.getElementById('ownerNombre').value;
                var apellido = document.getElementById('ownerApellido').value;
                var telefono = document.getElementById('ownerTelefono').value;
                var direccion = document.getElementById('ownerDireccion').value;

                var updatedPropietario = {
                    id: ownerId,
                    nombre: nombre,
                    apellido: apellido,
                    telefono: telefono,
                    direccion: direccion
                };

                fetch('/api/propietarios/' + ownerId, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(updatedPropietario)
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        } else if (response.status === 404) {
                            throw new Error('Propietario no encontrado');
                        } else {
                            throw new Error('Error al actualizar el propietario: ' + response.statusText);
                        }
                    })
                    .then(data => {
                        console.log('Propietario actualizado:', data);
                        alert('Propietario actualizado con éxito!');
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Hubo un error al actualizar el propietario: ' + error.message);
                    });

                var modalInstance = bootstrap.Modal.getInstance(editOwnerModal);
                if (modalInstance) {
                    modalInstance.hide();
                }
            });
        }
    }

    // --- Lógica del Botón de Eliminar Propietario (sin cambios) ---
    if (deleteOwnerButton) {
        deleteOwnerButton.addEventListener('click', function() {
            var ownerIdToDelete = document.getElementById('ownerId').value;

            if (confirm('¿Estás seguro de que deseas eliminar este propietario y todas sus mascotas asociadas? Esta acción es irreversible.')) {
                fetch('/api/propietarios/' + ownerIdToDelete, {
                    method: 'DELETE'
                })
                    .then(response => {
                        if (response.ok) {
                            alert('Propietario eliminado con éxito!');
                            var modalInstance = bootstrap.Modal.getInstance(editOwnerModal);
                            if (modalInstance) {
                                modalInstance.hide();
                            }
                            location.reload();
                        } else if (response.status === 404) {
                            throw new Error('Propietario no encontrado.');
                        } else {
                            throw new Error('Error al eliminar el propietario: ' + response.statusText);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Hubo un error al eliminar el propietario: ' + error.message);
                    });
            }
        });
    }

    // --- LÓGICA PARA EL MODAL DE EDICIÓN DE GATO (sin cambios) ---
    if (editPetModal) {
        editPetModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;

            var petId = button.getAttribute('data-pet-id');
            var petNombre = button.getAttribute('data-pet-nombre');
            var petRaza = button.getAttribute('data-pet-raza');
            var petEdad = button.getAttribute('data-pet-edad');
            var petPropietarioId = button.getAttribute('data-pet-propietario-id');

            var modalPetIdInput = editPetModal.querySelector('#petId');
            var modalPetNombreInput = editPetModal.querySelector('#petNombre');
            var modalPetRazaInput = editPetModal.querySelector('#petRaza');
            var modalPetEdadInput = editPetModal.querySelector('#petEdad');
            var modalPetOwnerIdInput = editPetModal.querySelector('#petOwnerId');

            if (modalPetIdInput) modalPetIdInput.value = petId;
            if (modalPetNombreInput) modalPetNombreInput.value = petNombre;
            if (modalPetRazaInput) modalPetRazaInput.value = petRaza;
            if (modalPetEdadInput) modalPetEdadInput.value = petEdad;
            if (modalPetOwnerIdInput) modalPetOwnerIdInput.value = petPropietarioId;

            var modalTitle = editPetModal.querySelector('.modal-title');
            if (modalTitle) modalTitle.textContent = 'Editar Gato: ' + petNombre;
        });

        var savePetChangesButton = document.getElementById('savePetChanges');
        if (savePetChangesButton) {
            savePetChangesButton.addEventListener('click', function() {
                var petId = document.getElementById('petId').value;
                var petNombre = document.getElementById('petNombre').value;
                var petRaza = document.getElementById('petRaza').value;
                var petEdad = document.getElementById('petEdad').value;
                var petPropietarioId = document.getElementById('petOwnerId').value;

                var updatedGato = {
                    id: petId,
                    nombre: petNombre,
                    raza: petRaza,
                    edad: petEdad,
                };

                fetch('/api/gatos/' + petId, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(updatedGato)
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        } else if (response.status === 404) {
                            throw new Error('Gato no encontrado');
                        } else {
                            throw new Error('Error al actualizar el gato: ' + response.statusText);
                        }
                    })
                    .then(data => {
                        console.log('Gato actualizado:', data);
                        alert('Gato actualizado con éxito!');
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Hubo un error al actualizar el gato: ' + error.message);
                    });

                var modalInstance = bootstrap.Modal.getInstance(editPetModal);
                if (modalInstance) {
                    modalInstance.hide();
                }
            });
        }

        if (deletePetButton) {
            deletePetButton.addEventListener('click', function() {
                var petIdToDelete = document.getElementById('petId').value;
                var petOwnerId = document.getElementById('petOwnerId').value;

                if (confirm('¿Estás seguro de que deseas eliminar este gato? Esta acción es irreversible.')) {
                    fetch('/api/gatos/' + petIdToDelete, {
                        method: 'DELETE'
                    })
                        .then(response => {
                            if (response.ok) {
                                alert('Gato eliminado con éxito!');
                                var modalInstance = bootstrap.Modal.getInstance(editPetModal);
                                if (modalInstance) {
                                    modalInstance.hide();
                                }
                                location.reload();
                            } else if (response.status === 404) {
                                throw new Error('Gato no encontrado.');
                            } else {
                                throw new Error('Error al eliminar el gato: ' + response.statusText);
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                            alert('Hubo un error al eliminar el gato: ' + error.message);
                        });
                }
            });
        }
    }

    // --- NUEVA LÓGICA PARA EL MODAL DE AGREGAR PROPIETARIO ---
    if (addOwnerModal) {
        addOwnerModal.addEventListener('show.bs.modal', function () {
            // Opcional: Limpiar el formulario cada vez que se abre el modal
            document.getElementById('addOwnerForm').reset();
        });

        if (createOwnerButton) {
            createOwnerButton.addEventListener('click', function() {
                var nombre = document.getElementById('newOwnerNombre').value;
                var apellido = document.getElementById('newOwnerApellido').value;
                var telefono = document.getElementById('newOwnerTelefono').value;
                var direccion = document.getElementById('newOwnerDireccion').value;

                // Simple validación para campos requeridos
                if (!nombre || !apellido) {
                    alert('El nombre y el apellido son obligatorios.');
                    return;
                }

                var newPropietario = {
                    nombre: nombre,
                    apellido: apellido,
                    telefono: telefono,
                    direccion: direccion
                };

                // Enviar los datos del nuevo propietario a tu API REST (usando fetch API)
                fetch('/api/propietarios', { // Endpoint POST para crear un nuevo propietario
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(newPropietario)
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json(); // Si es OK, parsea el JSON de la respuesta
                        } else {
                            // Si hay un error, puedes intentar parsear el cuerpo del error si tu API lo devuelve
                            return response.json().then(errorData => {
                                throw new Error('Error al crear el propietario: ' + (errorData.message || response.statusText));
                            });
                        }
                    })
                    .then(data => {
                        console.log('Nuevo propietario creado:', data);
                        alert('Propietario creado con éxito!');
                        // Cierra el modal
                        var modalInstance = bootstrap.Modal.getInstance(addOwnerModal);
                        if (modalInstance) {
                            modalInstance.hide();
                        }
                        // Recarga la página para mostrar el nuevo propietario
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Hubo un error al crear el propietario: ' + error.message);
                    });
            });
        }
    }
});