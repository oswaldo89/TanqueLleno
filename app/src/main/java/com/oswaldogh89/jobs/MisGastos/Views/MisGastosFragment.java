package com.oswaldogh89.jobs.MisGastos.Views;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.arasthel.asyncjob.AsyncJob;
import com.github.pires.obd.commands.fuel.FuelLevelCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.oswaldogh89.jobs.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MisGastosFragment extends Fragment {
    /*@BindView(R.id.list_works)
    RecyclerView mRecyclerView;*/
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.txtResult)
    TextView txtResult;

    @BindView(R.id.txtPorcentaje)
    TextView txtPorcentaje;

    @BindView(R.id.txtVelocidad)
    TextView txtVelocidad;


    BluetoothSocket socket = null;
    String litrosPorcentaje = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_work_offers, container, false);
        ButterKnife.bind(this, v);

        ArrayList deviceStrs = new ArrayList();
        final ArrayList devices = new ArrayList();

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceStrs.add(device.getName() + "\n" + device.getAddress());
                devices.add(device.getAddress());
            }
        }

        // show list
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.select_dialog_singlechoice,
                deviceStrs.toArray(new String[deviceStrs.size()]));

        alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                String deviceAddress = devices.get(position).toString();

                Log.v("OSWALDO", "Device :: " + deviceAddress);

                BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                final BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);
                final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


                new AsyncJob.AsyncJobBuilder<Boolean>()
                        .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                            @Override
                            public Boolean doAsync() {
                                try {
                                    socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
                                    socket.connect();
                                    return true;
                                } catch (IOException e) {
                                    return false;
                                }
                            }
                        })
                        .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                            @Override
                            public void onResult(Boolean result) {
                                Toast.makeText(getActivity(), "Result was: " + result, Toast.LENGTH_SHORT).show();

                                if (result) {
                                    txtResult.setText("Conectado.!");

                                    /* va dentro de un thread */

                                    AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
                                        @Override
                                        public void doOnBackground() {

                                            // execute commands
                                            try {
                                                new EchoOffCommand().run(socket.getInputStream(), socket.getOutputStream());
                                                new LineFeedOffCommand().run(socket.getInputStream(), socket.getOutputStream());
                                                new TimeoutCommand(125).run(socket.getInputStream(), socket.getOutputStream());
                                                new SelectProtocolCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
                                                new AmbientAirTemperatureCommand().run(socket.getInputStream(), socket.getOutputStream());

                                            } catch (Exception e) {
                                                // handle errors
                                            }

                                            FuelLevelCommand fuel = new FuelLevelCommand();

                                            while (!Thread.currentThread().isInterrupted()) {


                                                try {

                                                    fuel.run(socket.getInputStream(), socket.getOutputStream());
                                                    Log.d("OSWALDO_ODB2_GAS", "1: " + fuel.getFormattedResult());
                                                    Log.d("OSWALDO_ODB2_GAS", "2: " + fuel.getCalculatedResult());
                                                    Log.d("OSWALDO_ODB2_GAS", "3: " + fuel.getResult());
                                                    Log.d("OSWALDO_ODB2_GAS", "4: " + fuel.getFuelLevel());
                                                    litrosPorcentaje = fuel.getFormattedResult();
                                                } catch (Exception e) {
                                                    Log.d("OSWALDO_ODB2", "Exception: " + fuel.getFormattedResult());
                                                }

                                                // Send the result to the UI thread and show it on a Toast
                                                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                                                    @Override
                                                    public void doInUIThread() {

                                                        if (!litrosPorcentaje.equals("")) {
                                                            txtPorcentaje.setText(litrosPorcentaje);
                                                            
                                                            litrosPorcentaje = litrosPorcentaje.replace("%", "");
                                                            double litros = (Double.parseDouble(MisGastosFragment.this.litrosPorcentaje) * 52) / 100;

                                                            txtResult.setText("Litros :: " + litros);

                                                            //txtVelocidad.setText("Velocidad :: " + velocidad);
                                                        }

                                                    }
                                                });
                                            }


                                        }
                                    });

                                    /* dentro de thread */


                                } else {
                                    txtResult.setText("No se pudo conectar :(");
                                }

                            }
                        }).create().start();


            }
        });

        alertDialog.setTitle("Seleccione el Conector ODBII");
        alertDialog.show();


        return v;
    }

/*
    private WorksAdapter SetDataToList(ArrayList<WorkOffer> ListaEmpleos) {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return new WorksAdapter(getActivity(), ListaEmpleos);
    }
*/

}
