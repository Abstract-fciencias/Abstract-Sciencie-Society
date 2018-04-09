/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(menu);

var flag = false;

function menu () {
    $('#menu-icono').click(function () {
        if (flag) {
            $('#menu').animate({
                left:'-100%'
            });
        } else {
            $('#menu').animate({
                left: '0%'
            });
        }
        flag = !flag;
    });
}
