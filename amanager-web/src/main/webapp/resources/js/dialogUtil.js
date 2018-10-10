/**
 * Created by gmilazzo on 07/10/2018.
 */
var DialogUtil = {
    hideDialog: function (name, xhr, status, args) {
        if (!args.validationFailed && !args.isError) {
            PF(name).hide();
        }
    }
};
