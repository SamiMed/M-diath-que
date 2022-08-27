# Médiathèque 





Le travail consiste à coder un système très simplifié de gestion de documents pouvant se retrouver sous différents supports, 
en utilisant les concepts de la programmation orientée objet.
Notre médiathèque gère un catalogue des documents divers : livres, périodiques et DVD.


# Partie préposer : 

Le préposé peut inscrire un prêt, un retour, procéder au paiement d’une amende.
Le préposé peut aussi : acheter un nouveau document et l’ajouter au catalogue, supprimer un document du catalogue.
Le préposé peut aussi ajouter, supprimer et modifier un adhérent. Noter que seuls l’adresse et le numéro de téléphone peuvent être modifiés.

# Partie adhérent :

Les adhérents et le préposé peuvent consulter le catalogue.
Un adhérent est identifié par un numéro d’inscription, un numéro de téléphone, un nom, un prénom et une adresse. 
Tous les adhérents ont droit d’emprunter au maximum deux DVD, trois livres et un périodique. 
Pour chaque catégorie de documents (livres, périodiques et DVD), 
Il peut consulter son dossier à tout moment en fournissant son numéro de téléphone ou son nom et prénom.


![adherentPanel](https://user-images.githubusercontent.com/48655888/102696326-83dfa180-41fb-11eb-8430-63a1d287c838.GIF)



# Partie concernant les documents : 

la recherche doit être possible par auteur ou par mots clés.
Les résultats peuvent être affichés en ordre croissant ou décroissant selon les noms des auteurs ou les dates de publication.
La liste doit aussi informer sur la disponibilité des documents affichés.
Tous les documents sont identifiés par un numéro de document, un titre, le nombre de prêts ainsi que la date de 
publication. En plus de ces informations de base, les livres sont aussi caractérisés par un nom d’auteur, 
les périodiques possèdent un numéro de volume et un numéro de périodique, les DVD comportent un nom de réalisateur et le nombre de disques.

# Partie amende :

La durée d’un prêt est deux semaines pour les livres, 3 jours pour les périodiques et une semaine pour les DVD. 
Une amende de 0,50$ par jour est imposée pour tout retard lors de retour de documents. 
Un adhérant ayant une amende impayée ne peut emprunter un nouveau document.



Note : Ce projet à été développer en respectant les normes MVC






